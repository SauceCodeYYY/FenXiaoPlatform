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
<script type="text/javascript">
	var currentUser = '当前用户:<s:property value="#session.user.userName"/>';
	var nav = '<s:property value="tip"/>';
	if(nav == '' || nav.length <= 0){
		location = 'index.jsp';
	}
	var selfInfo = function(tabId, tabText){
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
			items: [{
				fieldLabel: '用户ID',
				name: 'userId',
				allowBlank: false,
				value: 1,
				disabled: true
			},{
				fieldLabel: '昵称',
				name: 'userName',
				allowBlank: false,
				value: 'Lisa',
				disabled: true
			},{
				fieldLabel: '邮箱',
				name: 'email',
				value: 'lisa@simpsons.com',
				allowBlank: false
			},{
				fieldLabel: '联系方式',
				name: 'phone',
				value: '555-111-1224',
				allowBlank: false
			},{
				fieldLabel: '账户余额',
				name: 'balance',
				value: '0.0',
				disabled: true
			},{
				fieldLabel: '权限',
				name: 'role',
				allowBlank: false,
				value: '管理员',
				disabled: true
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
			}],
		});
		createTab(tabId, tabText, formPanel);
	};
	
	var selfAddBalance = function(tabId, tabText){
		Ext.create('Ext.data.Store', {
			storeId:'addBalanceStore',
			autoLoad: true,
			fields:['编号', '银行', '户名', '充值金额', '汇款账户名', '提交日期', '充值状态', '备注'],
			proxy: {
				type: 'ajax',
				url: 'data/selfAddBalance.json',
				reader: {
					type: 'json',
					root: 'items'
				}
			}
		});
	
		var gridPanel = Ext.create('Ext.grid.Panel', {
			store: Ext.data.StoreManager.lookup('addBalanceStore'),
			columns: [
				{ text: '编号', dataIndex: '编号', flex: 1 },
				{ text: '银行', dataIndex: '银行', flex: 3 },
				{ text: '户名', dataIndex: '户名', flex: 3 },
				{ text: '充值金额', dataIndex: '充值金额', flex: 3 },
				{ text: '汇款账户名（淘宝订单号）', dataIndex: '汇款账户名', flex: 3 },
				{ text: '提交日期', dataIndex: '提交日期', flex: 3 },
				{ text: '充值状态', dataIndex: '充值状态', flex: 3 },
				{ text: '备注', dataIndex: '备注', flex: 3 }
			]
		});
		
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
			items: [
				{
					text: '充值',
					handler : function(){
						var window = Ext.create('Ext.window.Window', {
							title: '充值信息',
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
								xtype: "combo",
								fieldLabel: "充值方式",
                   				store: new Ext.data.SimpleStore({
									data: [
										[1, '支付宝'],
										[2, '中国银行'],
										[3, '工商银行'],
										[4, '建设银行'],
										[5, '农业银行'],
									],
									id: 0,
									fields: ['value', 'text']
								}),
								valueField: 'value',
								displayField: 'text',
								editable: false
							},{
								fieldLabel: '银行账户',
								name: 'bankAccount',
								allowBlank: false,
							},{
								fieldLabel: '户名',
								name: 'toUser',
								allowBlank: false
							},{
								xtype: "datefield",
								fieldLabel: '日期',
								name: 'phone',
								value: new Date(),
								allowBlank: false,
								disabled: true
							},{
								fieldLabel: '充值金额',
								name: 'amount',
							},{
								fieldLabel: '淘宝订单编号',
								name: 'orderId',
								allowBlank: false,
							},{
								fieldLabel: '汇款账户名',
								name: 'fromUser',
								allowBlank: false,
							},{
								xtype: 'textareafield',
								fieldLabel: '备注',
								name: 'comment',
								allowBlank: true,
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
							}],
						});
						window.add(formPanel);
					}
				},
				{
					text : '充值说明'
				},
			]
		});
		
		createTab(tabId, tabText, gridPanel, toolbar);
	};
	
	var selfWithdrawBalance = function(tabId, tabText){
		Ext.create('Ext.data.Store', {
			storeId:'withdrawStore',
			autoLoad: true,
			fields:['编号', '银行', '户名', '汇款账户名', '汇款账户名', '提现金额', '提交日期', '状态', '备注'],
			proxy: {
				type: 'ajax',
				url: 'data/selfWithdrawBalance.json',
				reader: {
					type: 'json',
					root: 'items'
				}
			}
		});
	
		var gridPanel = Ext.create('Ext.grid.Panel', {
			store: Ext.data.StoreManager.lookup('withdrawStore'),
			columns: [
				{ text: '编号', dataIndex: '编号', flex: 1 },
				{ text: '银行', dataIndex: '银行', flex: 3 },
				{ text: '户名', dataIndex: '户名', flex: 3 },
				{ text: '汇款账户名（支付宝订单号）', dataIndex: '汇款账户名', flex: 3 },
				{ text: '提现金额', dataIndex: '提现金额', flex: 3 },
				{ text: '提交日期', dataIndex: '提交日期', flex: 3 },
				{ text: '状态', dataIndex: '状态', flex: 3 },
				{ text: '备注', dataIndex: '备注', flex: 3}
			]
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
								allowBlank: false,
							},{
								fieldLabel: '支付宝户名',
								name: 'account',
								allowBlank: false,
							},{
								xtype: "datefield",
								fieldLabel: '日期',
								name: 'phone',
								value: new Date(),
								allowBlank: false,
								disabled: true
							},{
								fieldLabel: '提现金额',
								name: 'amount',
							},{
								xtype: 'textareafield',
								fieldLabel: '备注',
								name: 'comment',
								allowBlank: true,
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
							}],
						});
						window.add(formPanel);
					}
				},
				{
					text : '充值说明'
				},
			]
		});
		
		createTab(tabId, tabText, gridPanel, toolbar);
	};
	
	var userInfo = function(tabId, tabText){
		Ext.create('Ext.data.Store', {
			storeId:'userStore',
			autoLoad: true,
			fields:['userId', 'userName', 'email', 'phone', 'role', 'points', 'wwId', 'balance'],
			proxy: {
				type: 'ajax',
				url: 'findAllUser.action',
				reader: {
					type: 'json',
					root: 'root',
					totalProperty: 'totalProperty'
				}
			}
		});
		
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
			items: [
				{
					text: '添加用户',
					iconCls : 'icon-add',
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
							url: 'saveUser.action',
							// Fields will be arranged vertically, stretched to full width
							layout: 'anchor',
							defaults: {
								anchor: '100%'
							},
				
							// The fields
							defaultType: 'textfield',
							items: [{
								fieldLabel: '用户名',
								name: 'user.userName',
								allowBlank: false
							},{
								fieldLabel: '密码',
								name: 'user.password',
								inputType: 'password',
								allowBlank: false
							},{
								fieldLabel: '邮箱',
								name: 'user.email',
								allowBlank: false
							},{
								fieldLabel: '联系方式',
								name: 'user.phone',
								allowBlank: false
							},{
								fieldLabel: '角色',
								name: 'user.role',
								allowBlank: false
							},{
								xtype: 'numberfield',
								fieldLabel: '积分',
								name: 'user.points'
							},{
								xtype: 'numberfield',
								fieldLabel: '余额',
								name: 'user.balance'
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
											   window.close();
											   Ext.data.StoreManager.lookup('userStore').reload();
											},
											failure: function(form, action) {
												Ext.Msg.alert('Failed', action.result.msg);
											}
										});
									}
								}
							}],
						});
						window.add(formPanel);
					}
				},
				{
					text : '编辑用户',
					iconCls : 'icon-edit',
					handler : function(){
								var record = gridPanel.getSelectionModel().getSelection()[0];
								if(record){
									var window = Ext.create('Ext.window.Window', {
										title: '编辑用户',
										layout: 'fit',
										modal: true
									}).show();
												
									var formPanel = Ext.create('Ext.form.Panel', {
										bodyPadding: 5,
										width: 500,
										// The form will submit an AJAX request to this URL when submitted
										url: 'updateUser.action',
										// Fields will be arranged vertically, stretched to full width
										layout: 'anchor',
										defaults: {
											anchor: '100%'
										},
							
										// The fields
										defaultType: 'textfield',
										items: [{
											fieldLabel: '用户ID',
											value: record.getData().userId,
											name: 'user.userId',
											readOnly: true
										},{
											fieldLabel: '用户名',
											value: record.getData().userName,
											name: 'user.userName',
											readOnly: true
										},{
											fieldLabel: '邮箱',
											name: 'user.email',
											value: record.getData().email,
											allowBlank: false
										},{
											fieldLabel: '联系方式',
											name: 'user.phone',
											value: record.getData().phone,
											allowBlank: false
										},{
											fieldLabel: '角色',
											name: 'user.role',
											value: record.getData().role,
											allowBlank: false
										},{
											xtype: 'numberfield',
											fieldLabel: '积分',
											value: record.getData().points,
											name: 'user.points'
										},{
											xtype: 'numberfield',
											fieldLabel: '余额',
											value: record.getData().balance,
											name: 'user.balance'
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
														   window.close();
														   Ext.data.StoreManager.lookup('userStore').reload();
														},
														failure: function(form, action) {
															Ext.Msg.alert('Failed', action.result.msg);
														}
													});
												}
											}
										}],
									});
									window.add(formPanel);
								}
							}
				},
				{
					text : '删除用户',
					iconCls : 'icon-del',
					handler : function() {
						var record = gridPanel.getSelectionModel().getSelection();
						if (record) {
							Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : 'deleteUser.action',
										params : {
											userId : record[0].getData().userId
										},
										success : function() {
											Ext.data.StoreManager.lookup('userStore').reload();
										},
										failure : function() {
											Ext.Msg.show({
												title : '错误提示',
												msg : '删除时发生错误!',
												buttons : Ext.Msg.OK,
												icon : Ext.Msg.ERROR
											});
										}
									});
								}
							});
						}
					}
				}
			]
		});
		
		var gridPanel = Ext.create('Ext.grid.Panel', {
			store: Ext.data.StoreManager.lookup('userStore'),
			columns: [
				{ text: '用户ID', dataIndex: 'userId', flex: 1 },
				{ text: '用户名', dataIndex: 'userName', flex: 3 },
				{ text: '邮箱', dataIndex: 'email', flex: 3 },
				{ text: '联系方式', dataIndex: 'phone', flex: 3 },
				{ text: '角色', dataIndex: 'role', flex: 3 },
				{ text: '积分', dataIndex: 'points', flex: 3},
				{ text: '旺旺ID', dataIndex: 'wwId', flex: 3},
				{ text: '余额', dataIndex: 'balance', flex:3 }
			],
			dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store: Ext.data.StoreManager.lookup('userStore'),   // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
		    }],
		});
		
		createTab(tabId, tabText, gridPanel, toolbar);
	};
	
	
	function createTab(tabId, tabText, cmp, toolbar){
		var tab = Ext.create('Ext.panel.Panel', {
			id: tabId,
			title: tabText,
			closable: true
		});	
		if (toolbar) tab.add(toolbar);
		tab.add(cmp);
		rightPanel.add(tab);
		tab.show();
	}
	
	Ext.onReady(function() {
		// 上，logo 区域
		this.topPanel = Ext.create('Ext.panel.Panel', {
			region : 'north',
			height : 55
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
			layout : 'accordion',
			collapsible : true
		});
		
		// 右，内容tab
		this.rightPanel = Ext.create('Ext.tab.Panel', {
			region : 'center',
			layout : 'fit',
			minTabWidth : 120,
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
	                              			case 5: selfInfo(id, text); break;
	                              			case 6: selfAddBalance(id, text); break;
	                              			case 7: selfWithdrawBalance(id, text); break;
	                              			case 14: userInfo(id, text); break;
	           
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
			items : [ this.topPanel, this.topInfo, this.leftPanel, this.rightPanel ]
		});
	});
</script>
<body>
</body>
</html>
