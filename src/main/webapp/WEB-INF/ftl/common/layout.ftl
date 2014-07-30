<#--
* 页面布局宏。
* @author yangyong.
* @ since 2014-04-17.
-->
<#--head-->
<#macro layout_base_head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
	<!-- Core CSS - Include with every page -->
	<link rel="stylesheet" href="<@s.url "/resources/css/bootstrap.min.css"/>"/>
	<link rel="stylesheet" href="<@s.url "/resources/css/font-awesome.css"/>"/>
	<!-- SB Admin CSS - Include width every page-->
	<link rel="stylesheet" href="<@s.url "/resources/css/sb-admin.css"/>"/>
	<!--[if lt IE 9]>
		<script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
		<script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	<#nested/>
</#macro>
<#--基本角色加载-->
<#macro layout_base_js>
	<!--Core Scripts-->
	<script src="<@s.url "/resources/js/jquery-1.10.1.min.js"/>"></script>
	<script src="<@s.url "/resources/js/bootstrap.min.js"/>"></script>
	<script src="<@s.url "/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
	
	<!--SB Admin Scripts -Include with every page-->
	<script src="<@s.url "/resources/js/sb-admin.js"/>"></script>
</#macro>
<#--最基本布局-->
<#macro layout_base title="默认页面" head="">
	<!DOCTYPE html>
	<html>
		<head>
			<title>${title}</title>
			<@layout_base_head>${head}</@layout_base_head>
		</head>
		<body>
			<#nested/>
			<@layout_base_js/>
		</body>
	</html>
</#macro>
<#--banner top 布局--->
<#macro layout_top>
	<!--begin banner-->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">网校平台<small>后台管理系统</small></a>
		</div><!--/.navbar-header-->
		
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-messages">
					<li>
						<a href="#">
							<div>
								<strong>John Smith</strong>
								<span class="pull-right text-muted">
									<em>yesterday</em>
								</span>
							</div>
							<div>Lorem ipsum dolor sit amet,consectetur adipiscing elit,pellentesque eleifend...</div>
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="#">
							<div>
								<strong>John Smith</strong>
								<span class="pull-right text-muted">
									<em>yesterday</em>
								</span>
							</div>
							<div>Lorem ipsum dolor sit amet,consectetur adipiscing elit,pellentesque eleifend...</div>
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<a class="text-center" href="#">
							<strong>Read All Message</strong>
							<i class="fa fa-angle-right"></i>
						</a>
					</li>
				</ul><!--/.dropdown-messages-->
			</li><!--/.dropdown-->
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-alerts">
					<li>
						<a href="#">
							<div>
								<i class="fa fa-comment fa-fw"></i> New Comment
								<span class="pull-right text-muted small">4 minutes ago</span>
							</div>
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="#">
							<div>
								<i class="fa fa-twitter fa-fw"></i> New Comment
								<span class="pull-right text-muted small">14 minutes ago</span>
							</div>
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<a class="text-center" href="#">
							<strong>See All Alerts</strong>
							<i class="fa fa-angle-right"></i>
						</a>
					</li>
				</ul><!--/.dropdown-alerts-->
			</li><!--/.dropdown-->
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a></li>
					<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings </a></li>
					<li class="divider"></li>
					<li><a href="#"><i class="fa fa-sign-out fa-fw"></i> Logout </a></li>
				</ul>
			</li><!--/.dropdown-user-->
		</ul><!--/.navbar-top-links -->
		 
		 <div class="navbar-default navbar-static-side" role="navigation">
                <div class="sidebar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="index.html"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Charts<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Flot Charts</a>
                                </li>
                                <li>
                                    <a href="#">Morris.js Charts</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-table fa-fw"></i> Tables</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-edit fa-fw"></i> Forms</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> UI Elements<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Panels and Wells</a>
                                </li>
                                <li>
                                    <a href="#">Buttons</a>
                                </li>
                                <li>
                                    <a href="#">Notifications</a>
                                </li>
                                <li>
                                    <a href="#">Typography</a>
                                </li>
                                <li>
                                    <a href="#">Grid</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Second Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Second Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Blank Page</a>
                                </li>
                                <li>
                                    <a href="#">Login Page</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                    <!-- /#side-menu -->
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!--/.navbar-static-side-->
	</nav>
	<!--end banner-->
</#macro>
<#--footer-->
<#macro layout_footer>
	<!--begin footer-->
	<!--end footer-->
</#macro>
<#--列表操作页布局-->
<#macro layout_list  title="默认页面" head="">
	<@layout_base title=title head=head>
		<div id="wrapper">
			 <#--banner-->
			 <@layout_top/>
			 <div id="page-wrapper">
			 	<div class="row">
			 		<div class="col-lg-12">
			 			<#nested/>
			 		</div><!--/.col-lg-12-->
			 	</div><!--/.row-->
			 </div><!--/#page-wrapper-->
			 <#--footer-->
			 <@layout_footer/>
		 </div><!--/#wrapper-->
	</@layout_base>
</#macro>