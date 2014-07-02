<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<link href="js/extjs/ext-theme-classic-all.css" rel="stylesheet" type="text/css" />
<link href="css/users.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/extjs/bootstrap.js"></script>
<script type="text/javascript" src="/bmsh/js/RowExpander.js"></script>
<script type="text/javascript" src="/bmsh/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/selfCenter.js"></script>
<script type="text/javascript" src="js/prodCenter.js"></script>
<script type="text/javascript" src="js/manageCenter.js"></script>
<script type="text/javascript"><!--
	// var currentUser = '当前用户:<s:property value="#session.user.userName"/>';
	var nav = '<s:property value="tip"/>';
	if(nav == '' || nav.length <= 0){
		location = 'index.jsp';
	}
	var userId = "${ user.userId }";
	
	Ext.Ajax.on("requestexception",function(conn,response,options){
		if(response.status==408){
			window.location="login.jsp";
		}
	});
	
	var selfWithdraw = function(tabId, tabText){
		var withdrawStore = Ext.create('Ext.data.Store', {
			storeId:'withdrawStore',
			autoLoad: true,
			fields:['编号', '银行', '户名', '汇款账户名', '汇款账户名', '提现金额', '提交日期', '状态', '备注'],
			proxy: {
				type: 'ajax',
				url: 'data/selfWithdraw.json',
				reader: {
					type: 'json',
					root: 'items'
				}
			}
		});
	
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
			items: [
				{
					text: '提现',
					handler : function(){
						var window = Ext.create('Ext.window.Window', {
							title: '提现信息',
							layout: 'fit',
							modal: true
						}).show();
									
						var formPanel = Ext.create('Ext.form.Panel', {
							bodyPadding: 5,
							width: 500,
							// The form will submit an AJAX request to this URL when submitted
							url: '#',
							// Fields will be arranged vertically, stretched to full width
							layout: 'anchor',
							defaults: {
								anchor: '100%'
							},
				
							// The fields
							defaultType: 'textfield',
							items: [
							{
								fieldLabel: '申请人',
								name: 'user',
								allowBlank: false
							},{
								fieldLabel: '支付宝户名',
								name: 'account',
								allowBlank: false
							},{
								xtype: "datefield",
								fieldLabel: '日期',
								name: 'phone',
								value: new Date(),
								allowBlank: false,
								disabled: true
							},{
								fieldLabel: '提现金额',
								name: 'amount'
							},{
								xtype: 'textareafield',
								fieldLabel: '备注',
								name: 'comment',
								allowBlank: true
							}],
				
							// Reset and Submit buttons
							buttons: [{
								text: 'Reset',
								handler: function() {
									this.up('form').getForm().reset();
								}
							}, {
								text: 'Submit',
								formBind: true, //only enabled once the form is valid
								disabled: true,
								handler: function() {
									var form = this.up('form').getForm();
									if (form.isValid()) {
										form.submit({
											success: function(form, action) {
											   Ext.Msg.alert('Success', action.result.msg);
											},
											failure: function(form, action) {
												Ext.Msg.alert('Failed', action.result.msg);
											}
										});
									}
								}
							}]
						});
						window.add(formPanel);
					}
				},
				{
					text : '充值说明'
				},
			]
		});
		
		var gridPanel = Ext.create('Ext.grid.Panel', {
			store: withdrawStore,
			columns: [
				{ text: '编号', dataIndex: '编号', flex: 1 },
				{ text: '银行', dataIndex: '银行', flex: 3 },
				{ text: '户名', dataIndex: '户名', flex: 3 },
				{ text: '汇款账户名（支付宝订单号）', dataIndex: '汇款账户名', flex: 3 },
				{ text: '提现金额', dataIndex: '提现金额', flex: 3 },
				{ text: '提交日期', dataIndex: '提交日期', flex: 3 },
				{ text: '状态', dataIndex: '状态', flex: 3 },
				{ text: '备注', dataIndex: '备注', flex: 3}
			],
			dockedItems: [toolbar, {
		        xtype: 'pagingtoolbar',
		        store: withdrawStore,   // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
	  		}]
		});
		
		createTab(tabId, tabText, gridPanel);
	};
	
	
	
	function createTab(tabId, tabText, cmp, layoutParam){
		var layout = 'fit';
		if (layoutParam) {
			layout = layoutParam;
		}
		var tab = Ext.create('Ext.panel.Panel', {
			id: tabId,
			title: tabText,
			closable: true,
			layout: layout,
			autoScroll: true
		});	
		tab.add(cmp);
		rightPanel.add(tab);
		tab.show();
	}
	
	Ext.onReady(function() {
		// 上，logo 区域
		this.topPanel = Ext.create('Ext.panel.Panel', {
			region : 'north',
			height : 59,
			html: '<a href="logout.action"><B>退出</B></a>&nbsp;&nbsp;'+
		  	    '<a href="tencent://message/?uin=531683074&Site=www.xinnki.com&Menu=yes" target="_blank" title="客服一">[客服1]</a><IMG height=12 src="images/qq.jpg" width=18></br>'+
     		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="tencent://message/?uin=1546026460&Site=www.xinnki.com&Menu=yes" target="_blank" title="客服一">[客服2]</a><IMG height=12 src="images/qq.jpg" width=18></br>'+
     		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="tencent://message/?uin=173185172&Site=www.xinnki.com&Menu=yes" target="_blank" title="客服一">[客服3]<IMG height=12 src="images/qq.jpg" width=18></a>',


			 bodyStyle: {  
                        //background: '#ffc',  
                        background: 'url(images/131415.jpg) no-repeat #FFFFFF center',  
                        padding: '10px'  ,
                   	    width:'1000px'
                      //  height:'45px'
                    }
			
		});


		// 上，信息栏
		this.topInfo = Ext.create('Ext.panel.Panel', {
			region: 'north',
			height: 30,
			title: '信息栏'
		});
		
		// 左，导航
		this.leftPanel = Ext.create('Ext.panel.Panel', {
			region : 'west',
			title : '导航栏',
			width : 230,
			//layout : 'accordion',
			collapsible : true
		});
		
		// 右，内容tab
		this.rightPanel = Ext.create('Ext.tab.Panel', {
			region : 'center',
			layout : 'fit',
			//bodyStyle:"background-image:url(images/2681.jpg)",
			 bodyStyle: {  
                        //background: '#ffc',  
                        background: 'url(images/902.jpg) no-repeat #FFFFFF center',  
                        padding: '10px'  ,
                        width:'300px',
                        height:'250px'
                    },  
			minTabWidth : 120
		});
		
		// 生成导航树
		var buildTree = function(json) {  
  		      return Ext.create('Ext.tree.Panel', {  
                       rootVisible : false,  
                       border : false,  
                       store : Ext.create('Ext.data.TreeStore', {  
                                   root : {  
                                       expanded : true,  
                                       children : json.children  
                                   }  
                               }),  
                       listeners : {  
                           'itemclick' : function(view, record, item,  
                                   index, e) {  
                               var id = record.get('id'); 
                               var text = record.get('text');  
                               var leaf = record.get('leaf');  
                               if (leaf) {  
	                              	var tab = rightPanel.getComponent(id);
	                              	if (tab){
	                              		tab.show();
	                              	} else {
	                              		switch (id){
	                              			case "selfInfo": selfInfo(id, text); break;
	                              			case "selfRecharge": selfRecharge(id, text); break;
	                              			case "selfWithdraw": selfWithdraw(id, text); break;
	                              			case "selfAddr": selfAddr(id, text); break;
	                              			case "selfPayPwd": selfPayPwd(id, text); break;
	                              			case "selfOrders": selfOrders(id, text); break;
	                              			case "selfExpress": selfExpress(id, text); break;
	                              			case "prodSearch": prodSearch(id, text); break;
	                              			case "shoppingCart": shoppingCart(id, text); break;
	                              			case "manageUsers": userInfo(id, text); break;
											case "manageProds": prodInfo(id, text); break;
	           								case "manageRecharges": rechargeInfo(id, text); break;
	           								case "manageOrders": orderInfo(id, text); break;
	           								case "manageDiscount": discountInfo(id, text); break;
									        case "yunfeiOrders": yunfeiOrders(id, text); break;
    									    case "fankuiOrders": fankuiOrders(id, text); break;
    									    // case "gonggaoOrders": gonggaoOrders(id, text); break;
	                              		}
	                               }
                               }  
                           },  
                           scope : this  
                       }  
                   });  
            };  

		// 加载导航树
		Ext.Ajax.request({
			url : (nav == 'admin')? 'json/left-nav-admin.json':'json/left-nav-user.json',
			success : function(response) {
				// alert(response.responseText);
				var json = Ext.decode(response.responseText);
				Ext.each(json.data, function(el) {
					// alert(el.id + " " + el.text + " " + el.func);
					var panel = Ext.create('Ext.panel.Panel', {
						id : el.id,
						title : el.text,
						layout : 'fit'
					});
					panel.add(buildTree(el));
					leftPanel.add(panel);
				});
			},
			failure : function(request) {
				Ext.MessageBox.show({
					title : '操作提示',
					msg : "连接服务器失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			},
			method : 'post'
		});

		Ext.create('Ext.container.Viewport', {
			layout : 'border',
			renderTo : Ext.getBody(),
			items : [ this.topPanel, /* this.topInfo, */ this.leftPanel, this.rightPanel ]
		});
		
		gonggaoOrders("announcementOrders", "公告");
	});
</script>
<body>
</body>
</html>
