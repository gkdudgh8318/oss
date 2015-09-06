<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>사용자 관리</title>
<link rel="stylesheet" type="text/css" href="/css/jquery-ui.jqgrid.css" />
<style type="text/css">
div.controllBox {
    float: right;
    height: 21px;
    line-height: 21px;
}
</style>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/css/jquery-ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" /> 
<link href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" media="all" />
<script src="/js/jquery-2.1.1.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/jquery-ui.jqgrid.min.js"></script>
<script src="/js/jquery-ui.jqgrid.locale-kr.js"></script>
<script type="text/javascript">
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
        console.log(JSON.stringify(jsonObj, "", "  "));
        
    } catch (e) {}
}

function init() {
    <%-- 사용자 관리 Grid --%>
   /*  var lastSelected = null; */
    
    grid = $("#userGrid");
    grid.jqGrid({
         url: "/admin/user/list"
     	, type : "POST"         
        , datatype: "json"
        , width: 1350
		, loadonce:false
		, height:350
		, search:true
        , colNames: ["MODE", "Email", "비밀번호", "성씨", "이름", "주민번호", "카드번호", "계좌번호", "휴대전화", "유선전화", "생년월일", "주소"]
        , colModel: [
              {name: "MODE",    index: "MODE",    width:   0, hidden: true}
             ,{name: "MAIL",    index: "MAIL",    width: 200, align: "left",   editable:  false}
             ,{name: "PASS_WD", index: "PASS_WD", width: 100, align: "left",   editable:  false}
             ,{name: "LAST_NM", index: "LAST_NM", width: 100, align: "left",   editable:  false}
             ,{name: "FRST_NM", index: "FRST_NM", width: 100, align: "left",   editable:  false}
             ,{name: "REGI_NO", index: "REGI_NO", width: 200, align: "center", editable:  false}
             ,{name: "CARD_NO", index: "CARD_NO", width: 200, align: "center", editable:  false}
             ,{name: "ACCT_NO", index: "ACCT_NO", width: 200, align: "center", editable:  false}
             ,{name: "CELL_NO", index: "CELL_NO", width: 200, align: "center", editable:  false}
             ,{name: "TELE_NO", index: "TELE_NO", width: 200, align: "center", editable:  false}
             ,{name: "BRTH_DT", index: "BRTH_DT", width: 100, align: "center", editable:  false}
             ,{name: "ADDR",    index: "ADDR",    width: 200, align: "left",   editable:  false}
        ]
       , viewrecords: true
       , emptyrecords: "조회된 데이터가 없습니다."
       , scroll: true
	   , rowNum:15 // 한 화면에 보여줄 갯수
	   , rowList:[15, 30, 50]
	   , pager: '#pager'
	   , recordpos: 'left'
	   , hidegrid: false
	   , editurl: 'clientArray'
	   , cellEdit : true
	   , cellsubmit : 'clientArray'
	   , caption:"사원 관리 Grid"// 차트 제목	   
       , beforeSelectRow : function(rowid, e){
			
	   		var grid_row =null;	
			if(grid_row == rowid){
				return;
			}

			grid_row = rowid;
			var row = $("#userGrid").getRowData(rowid);

	   		$("#userGrid").resetSelection();
	    	$("#userGrid").setSelection(rowid);

			gridInputData(row);
	    	return true;
	    	
		}, onSelectRow: function(id, status, e) {
/*              if (id && id !== lastSelected) {
                grid.jqGrid('saveRow', lastSelected);
                grid.jqGrid('editRow', id, {
                     keys: true // true : Enter - 수정 완료, Esc - 수정 취소, false : function 호출 버튼으로만 완료, 취소 가능
                    ,url: '/admin/user/save'
                    ,mtype : "POST"
                    ,successfunc: function(response) {
                         
                         logJSON(response);
                         
                         if (response && response.responseJSON && response.responseJSON.userdata) {
                             if (response.responseJSON.userdata.resultCode === '<spring:message code="common.success.code" />') {
                                 response.responseJSON.userdata.resultCode = '<spring:message code="common.complete.code" />';
                                 
                                 grid.jqGrid('saveRow', id);
                                 
                                 alert(response.responseJSON.userdata.resultMsg);
                             }
                         }
                     }
                });
                lastSelected = id;
            } */
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
            
            grid.jqGrid('setGridParam', {url: "", datatype: "local"});
            
            alert('조회 오류!!'); 
        }
    });
}

$(document).ready(function() {
	
	init();
	
    $('#addRow').click(function() {
    	grid.jqGrid('addRow', {
            rowID: 'MAIL'+ ++rowIdIndex
            
            /* addRowParams: {
                keys: true, // true : Enter - 수정 완료, Esc - 수정 취소, false : function 호출 버튼으로만 완료, 취소 가능
                url: '/admin/user/save',
                mtype : "POST",
                successfunc: function(response) {
                    
                    logJSON(response);
        			$("#MODE").val("update");
                    var result = false;
                    if (response && response.responseJSON && response.responseJSON.userdata) {
                        if (response.responseJSON.userdata.resultCode === '<spring:message code="common.success.code" />') {
                            response.responseJSON.userdata.resultCode = '<spring:message code="common.complete.code" />';
                            
                            grid.jqGrid('saveRow', 'MAIL'+ rowIdIndex);
                            
                            alert(response.responseJSON.userdata.resultMsg);
                            
                            result = true;
                        }
                    }
                    
                    return result;
                }
            } */
        });
    	document.frm.reset();
    	$('#MAIL').focus();
    });
    
    /* 저장 */
    $('#saveRow').click(function(){
		   	
    	$.ajax({
    		url: '/admin/user/save'
    		, dataType : "html"
    		, mtype:"POST"
    		, data:$("#frm").serialize()
    		, success : function(result){
    			
    			var grid_srow =  $( "#userGrid" ).getGridParam( "selrow" );

    			jQuery("#userGrid").trigger("reloadGrid",[{current:true}]);

    			$("#userGrid").jqGrid('setSelection', grid_srow);
    			
    			$("#MODE").val("M");
    			document.frm.reset();
    		}
    		, error  : function(result){}
    	});
    }).trigger("reloadGrid"); 
    
    /* 삭제 */
    $('#removeRow').click(function(){

    	$("#MODE").val("D");
    	$.ajax({
    		url: '/admin/user/remove'
    		, dataType : "html"
    		, mtype:"POST"
    		, data:$("#frm").serialize()
    		, success : function(result){
    			
    			var grid_srow =  $( "#userGrid" ).getGridParam( "selrow" );
    			jQuery("#userGrid").trigger("reloadGrid",[{current:true}]);
    			$("#userGrid").jqGrid('setSelection', grid_srow);
    			document.frm.reset();
    		}
    		, error  : function(result){}
    	});
    }).trigger("reloadGrid");
    
	/* 초기화 */
	$('#init').click(function() {
		document.frm.reset();

		$("#userGrid").setGridParam({
			url:null,
			cellEdt:true,
		});
		
		$("MODE").val("M");
	});
});

/* 데이터세팅 */
function gridInputData(row) {
	
	$("#MAIL").val(row.MAIL);
	$("#PASS_WD").val(row.PASS_WD);
	$("#LAST_NM").val(row.LAST_NM);
	$("#FRST_NM").val(row.FRST_NM);
	$("#REGI_NO").val(row.REGI_NO);
	$("#CARD_NO").val(row.CARD_NO);
	$("#ACCT_NO").val(row.ACCT_NO);
	$("#CELL_NO").val(row.CELL_NO);
	$("#TELE_NO").val(row.TELE_NO);
	$("#BRTH_DT").val(row.BRTH_DT);	
	$("#ADDR").val(row.ADDR);	
	$("#MODE").val("M");
	$("#MAIL").prop("readonly", true);
}
</script>
</head>
<body>
	<form method="post" name="frm" id="frm" >
		<input type="hidden" name="MODE" id="MODE" value="M" />	
		<div class="title"><img src="${pageContext.request.contextPath}/images/tit_t1_01.gif" alt="사용자관리" border="0" />
		    <div style="height: 27px; margin: 5px 0px 5px 10px;">
		        <div class="controllBox">
		        	<input type="button" id="addRow" value="추가" style="width: 50px;" />
			        <input type="button" id="saveRow" value="등록" style="width: 50px;" />
	   			    <input type="button" id="removeRow" value="삭제" style="width: 50px;" />			        
	   			    <input type="button" id="init" value="초기화" style="width: 50px;" />
		        </div>		        
		    </div>
	    </div>
	    <div class="grid_title" style="width:1200px;height:1px;">&nbsp;</div>
	    <div class="grid">
	        <table id="userGrid"></table>
	        <div id="pager"></div>
	    </div> 
		<!-- 입력창 -->
        <div>
          <table class="outline02">
            <thead>
              <tr>
                <th width="91" bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;Email</th>
                <th width="240" align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="MAIL" id="MAIL" class="text_29" />
                </th>
                <th width="91" bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;비밀번호</th>
                <th width="270" align="left"  bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="PASS_WD" id="PASS_WD" class="text_29"/>
                </th>
                <th width="91" bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;성씨</th>
                <th width="95" align="left"  bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="LAST_NM" id="LAST_NM" class="text_29 datepicker" />
                </th>
              </tr>
              <tr>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;이름</th>
                <th align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="FRST_NM" id="FRST_NM" class="text_29 datepicker" />
                </th>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;주민번호</th>
                <th width="270" align="left"  bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="REGI_NO" id="REGI_NO" class="text_29" />
                </th>
                <th width="91" bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;카드번호</th>
                <th align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="CARD_NO" id="CARD_NO" class="text_29" />
                </th>	                
              </tr>
              <tr>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;계좌번호</th>
                <th align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="ACCT_NO" id="ACCT_NO" class="text_29 datepicker" />
                </th>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;휴대전화</th>
                <th width="270" align="left"  bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="CELL_NO" id="CELL_NO" class="text_29" />
                </th>
                <th width="91" bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;유선전화</th>
                <th align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="TELE_NO" id="TELE_NO" class="text_29" />
                </th>	                
              </tr>	  
              <tr>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;생년월일</th>
                <th align="left" bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="BRTH_DT" id="BRTH_DT" class="text_29 datepicker" />
                </th>
                <th bgcolor="#ebe6dc" class="type_a" scope="col">&nbsp;주소</th>
                <th width="270" align="left"  bgcolor="#f7f5f1" scope="col">
                	<input type="text" name="ADDR" id="ADDR" class="text_29" />
                </th>
              </tr>	    	                          
            </thead>
          </table>
        </div>
        <!-- 입력창 -->		    
	</form>
</body>
</html>