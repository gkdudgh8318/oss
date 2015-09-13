<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>사용자 관리</title>
<link rel="stylesheet" type="text/css" media="all" href="/css/jquery-ui-1.10.4.custom.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="/css/jquery-ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="all" href="/css/style.css" />
<script src="/js/jquery-2.1.1.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/jquery-ui.jqgrid.min.js"></script>
<script src="/js/jquery-ui.jqgrid.locale-kr.js"></script>
<script>
var grid;
var rowIdIndex = 100; // 신규 행 추가시 사용될 ID index

/**
 * 문자열 console log
 *
 * @param msg
 */
function log(msg) {

    try {
        console.log(msg);

    } catch (e) {}
}

/**
 * JSON console log
 *
 * @param jsonObj
 */
function logJSON(jsonObj) {

    try {
        console.log(JSON.stringify(jsonObj, '', '  '));

    } catch (e) {}
}

function init() {
	
	var selectedRowId;
    <%-- 사용자 관리 Grid --%>
    grid = $('#userGrid');
    grid.jqGrid({
         url: '/admin/user/list'
        ,type : 'POST'
        ,datatype: 'json'
        ,width: 1460
        ,loadonce: false
        ,height: 350
        ,search: true
        ,colNames: ['MODE', 'Email', '비밀번호', '성씨', '이름', '주민번호', '카드번호', '계좌번호', '휴대전화', '유선전화', '생년월일', '주소']
        ,colModel: [
              {name: 'MODE',    index: 'MODE',    width:   0, hidden: true}
             ,{name: 'MAIL',    index: 'MAIL',    width: 150, align: 'left'  }
             ,{name: 'PASS_WD', index: 'PASS_WD', width: 100, align: 'left'  }
             ,{name: 'LAST_NM', index: 'LAST_NM', width: 100, align: 'left'  }
             ,{name: 'FRST_NM', index: 'FRST_NM', width: 100, align: 'left'  }
             ,{name: 'REGI_NO', index: 'REGI_NO', width: 150, align: 'center'}
             ,{name: 'CARD_NO', index: 'CARD_NO', width: 150, align: 'center'}
             ,{name: 'ACCT_NO', index: 'ACCT_NO', width: 150, align: 'center'}
             ,{name: 'CELL_NO', index: 'CELL_NO', width: 150, align: 'center'}
             ,{name: 'TELE_NO', index: 'TELE_NO', width: 150, align: 'center'}
             ,{name: 'BRTH_DT', index: 'BRTH_DT', width: 100, align: 'center'}
             ,{name: 'ADDR',    index: 'ADDR',    width: 150, align: 'left'  }
         ]
        ,viewrecords: true
        ,emptyrecords: '조회된 데이터가 없습니다.'
        ,scroll: true
        ,rowNum: 15
        ,rowList: [15, 30, 50]
        ,pager: '#pager'
        ,recordpos: 'left'
        ,hidegrid: false
        ,editurl: 'clientArray'
        ,cellEdit : false
        ,cellsubmit : 'clientArray'
        ,caption: '사원 관리 Grid'
        ,onSelectRow : function(rowId, status, e) {

            if (rowId === selectedRowId) {
                return false;
            } else {
            	selectedRowId = rowId;
            }

            var grid = $('#userGrid');
            var row = grid.getRowData(rowId);

            grid.resetSelection();
            grid.setSelection(rowId);

            copyDataToForm(row);

            return true;
        }
       ,loadComplete: function(response) {

            logJSON(response);

            if (response && response.userdata) {
                if (response.userdata.resultCode === '<spring:message code="common.success.code" />') {
                    response.userdata.resultCode = '<spring:message code="common.complete.code" />';

                    alert(response.userdata.resultMsg);
                }
            }
        }
       ,loadError: function(xhr, st, err) {

            log('Type: '+ st +'; Response: '+ xhr.status +' '+ xhr.statusText);

            grid.jqGrid('setGridParam', {url: '', datatype: 'local'});

            alert('조회 오류!!');
        }
    });
}

$(document).ready(function() {

    init();
    <%-- Grid 데이터 조회 --%>
    $('#retrieveData').click(function() {

        $('#userGrid').setGridParam({
             url: '/admin/user/list'
            ,postData : $('#frm').serialize()
            ,mtype: 'POST'
        }).trigger('reloadGrid');
    });
    <%-- 신규 행 추가 --%>
    $('#addRow').click(function() {
    	
        document.frm.reset();
        grid.jqGrid('addRow', {
            rowID: 'MAIL'+ ++rowIdIndex
        });
        $('#MAIL').prop('readonly', false).focus();
    });
    <%-- Grid 데이터 저장 --%>
    $('#saveData').click(function() {

        $.ajax({
             url: '/admin/user/save'
            ,dataType: 'json'
            ,mtype: 'POST'
            ,data:$('#frm').serialize()
            ,success : function(result) {

                document.frm.reset();
            	$('#userGrid').trigger('reloadGrid');
                $('#MODE').val('M');
            }
        });
    });
    <%-- 선택 행 삭제 --%>
    $('#removeRow').click(function() {

        $('#MODE').val('D');
        $.ajax({
             url: '/admin/user/remove'
            ,dataType: 'json'
            ,mtype: 'POST'
            ,data: $('#frm').serialize()
            ,success: function(result) {

                document.frm.reset();
                $('#userGrid').trigger('reloadGrid');
             }
        });
    });
    <%-- Grid & Form 초기화 --%>
    $('#resetAll').click(function() {
    	
        document.frm.reset();
        $('#userGrid').clearGridData();
        $('MODE').val('M');
    });
});
<%-- 선택 행 데이터 form으로 복사 --%>
function copyDataToForm(row) {

	for (var colName in row) {
	    $('#' + colName).val(row[colName]);
	}
    $('#MODE').val('M');
    $('#MAIL').prop('readonly', true);
}
</script>
</head>
<body>
<div class="contentWrapper">
    <%-- 제목 --%>
    <div class="titleSection">
        <h1>사용자 관리</h1>
    </div>
    <%-- 조회조건 --%>
    <div class="controlSection">
        <table>
            <colgroup>
                <col style="min-width: 100px; width: 100px" />
                <col style="min-width: 100px; width: 100px" />
                <col style="min-width: 100px; width: 100px" />
                <col style="width: auto" />
            </colgroup>
            <tbody>
                <tr>
                    <th>조회조건</th>
                    <td class="label">휴대전화</td>
                    <td><input type="text" name="S_CELL_NO" id="S_CELL_NO" /></td>
                    <td><div class="buttonGroup">
				            <button type="button" id="retrieveData">조회</button>
				            <button type="button" id="addRow">추가</button>
				            <button type="button" id="saveData">저장</button>
				            <button type="button" id="removeRow">삭제</button>
				            <button type="button" id="resetAll">초기화</button>
				        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="gridSection">
        <table id="userGrid"></table>
        <div id="pager"></div>
    </div>
    <%-- 입력창 --%>
    <div class="formSection">
		<form method="post" name="frm" id="frm">
		    <input type="hidden" name="MODE" id="MODE" value="M" />
	        <table>
	            <colgroup>
	                <col style="min-width: 100px; width: 100px" />
	                <col style="min-width: 100px; width: auto" />
	                <col style="min-width: 100px; width: 100px" />
	                <col style="min-width: 100px; width: auto" />
	                <col style="min-width: 100px; width: 100px" />
	                <col style="min-width: 100px; width: auto" />
	            </colgroup>
	            <tbody>
	                <tr>
	                    <th>Email</th>
	                    <td><input type="text" name="MAIL" id="MAIL" /></td>
	                    <th>비밀번호</th>
	                    <td><input type="text" name="PASS_WD" id="PASS_WD" /></td>
	                    <th>성씨</th>
	                    <td><input type="text" name="LAST_NM" id="LAST_NM" /></td>
	                </tr>
	                <tr>
	                    <th>이름</th>
	                    <td><input type="text" name="FRST_NM" id="FRST_NM" /></td>
	                    <th>주민번호</th>
	                    <td><input type="text" name="REGI_NO" id="REGI_NO" /></td>
	                    <th>카드번호</th>
	                    <td><input type="text" name="CARD_NO" id="CARD_NO" /></td>
	                </tr>
	                <tr>
	                    <th>계좌번호</th>
	                    <td><input type="text" name="ACCT_NO" id="ACCT_NO" /></td>
	                    <th>휴대전화</th>
	                    <td><input type="text" name="CELL_NO" id="CELL_NO" /></td>
	                    <th>유선전화</th>
	                    <td><input type="text" name="TELE_NO" id="TELE_NO" /></td>
	                </tr>
	                <tr>
	                    <th>생년월일</th>
	                    <td><input type="text" name="BRTH_DT" id="BRTH_DT" class="datepicker" /></td>
	                    <th>주소</th>
	                    <td><input type="text" name="ADDR" id="ADDR" /></td>
	                    <th></th>
	                    <td></td>
	                </tr>
	            </tbody>
	        </table>
		</form>
    </div>
    <%-- 입력창 --%>
</div>
</body>
</html>