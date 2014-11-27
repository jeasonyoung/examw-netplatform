<#--试卷试题通用macro-->
<#--选择题选项显示-->
<#macro paper_item_edit_opts_dg type>
<div data-options="region:'center',title:'选项',tools:[
			<@shiro.hasPermission name='${PER_UPDATE}'>{ iconCls:'icon-add',text:'添加选项', handler:function(){${dg}_add();}},</@shiro.hasPermission>
			<@shiro.hasPermission name='${PER_DELETE}'>{ iconCls:'icon-remove',text:'删除选项', handler:function(){${dg}_delete();}}</@shiro.hasPermission>
			],border:false">
	<table id="${dg}" class="easyui-datagrid" data-options="fit:true,fitColumns:true,rownumbers:true,border:true,striped:true,idField:'opt_id',
		onLoadError:function(e){<@error_dialog 'e'/>},onDblClickRow:function(rowIndex,rowData){${module}_edit_window('编辑选项',rowIndex,rowData);}">
		<thead>
			<tr>
				<th data-options="field:'opt_id',checkbox:true"></th>
				<th data-options="field:'opt_content', width:100,align:'left',formatter:function(value,row,index){ return value.replace(/<[^>]*>/g,''); }">选项内容</th>
				<th data-options="field:'opt',width:10,align:'center',formatter: function(value,row,index){return '<input type=\'${type}\' name=\'answer\' value=\''+row['opt_id'] +'\'/>';}">正确答案</th>
			</tr>
		</thead>
	</table>
</div>
</#macro>
<#--添加选项脚本-->
<#macro paper_item_edit_widow>
${module}_edit_window = function(title,index,row){
	var d = $("<div/>").dialog({
		title:title,width:600,height:200,modal:true,
		href:"<@s.url '/admin/teachers/practice/item/edit/option/${current_item_type_value}'/>",
		buttons:[
		<@shiro.hasPermission name="${PER_UPDATE}">
		{
			text:"保存",iconCls:"icon-save",
			handler:function(){
				var data = $("#admin_teachers_practice_item_${current_item_type_value}_edit_opt_form").form("serialize");
				if($.trim(data["opt_content"]) == ""){
					$.messager.alert("警告","请输入选项内容！");
					return;
				}
				var index = $("#${dg}").datagrid("getRowIndex",data["opt_id"]);
				if(index > -1) {
					$("#${dg}").datagrid("updateRow",{index:index, row : data});
				}else{
					$("#${dg}").datagrid("appendRow",data);
				}
				d.dialog("close");
			}
		},
		</@shiro.hasPermission>
		{
			text:"关闭",iconCls:"icon-cancel",
			handler:function(){ d.dialog("close");}
		}],
		onClose:function(){$(this).dialog("destroy");},
		onLoad:function(){if(row){$("#admin_teachers_practice_item_${current_item_type_value}_edit_opt_form").form("load",row);}}
	});
};
</#macro>
<#--选项删除-->
<#macro paper_item_edit_opts_delete>
<@shiro.hasPermission name="${PER_DELETE}">
	${dg}_delete = function(){
		var rows = $("#${dg}").datagrid("getChecked");
		if(rows && rows.length > 0){
			$.messager.confirm("确认","您是否确认删除选中的数据?",function(r){
				if(!r)return;
				for(var i = 0; i < rows.length; i++){
					var index = $("#${dg}").datagrid("getRowIndex",rows[i]["opt_id"]);
					if(index > -1){
						$("#${dg}").datagrid("deleteRow",index);
					}
				}
			});
		}else{
			$.messager.alert("提示","未选中须删除的数据！");
		}
	};
	</@shiro.hasPermission>
	</#macro>