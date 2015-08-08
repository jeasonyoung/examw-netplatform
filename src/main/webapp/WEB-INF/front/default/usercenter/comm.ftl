<#-- css/js -->
<#macro cssJs>
<link rel="stylesheet" href='<@s.url "/resources/front/css/pub.css"/>' type="text/css"/>
<link rel="stylesheet" href='<@s.url "/resources/front/css/vip.css"/>' type="text/css"/>
<!-- jquery -->
<script type="text/javascript" src="http://img.examw.com/jquery.js"></script>
<script type="text/javascript" src='<@s.url "/resources/front/js/new.js"/>'></script>
</#macro>

<#-- 顶部 -->
<#macro topHead>
<div class="top fl">
	<div class="main">
    	<div class="fl">
            <a href="http://www.examw.com/" class="logo fl" title="中华考试网"></a>
            <div class="topnva"><a href="tiku.html" title="题库" class="nvabox check-tiku"><em class="tiku"></em>题库</a></div>
            <div class="topnva check"><a href="#" title="课程" class="nvabox check-video"><em class="video"></em>课程</a></div>
        </div>
        <div class="fr">
        	<div class="topnva check"><a href="#" title="信息" class="nvabox check-vip"><em class="xinxi"></em><span>5</span></a></div>
        </div>
    </div>
</div>
</#macro>

<#-- 顶部第二栏 -->
<#macro secondHead>
<div class="menu-box fl">
	<div class="main">
    	<div class="kch-nva fl">全部课程</div>
        <div id="kch-position">
        <div id="sidebar2">
        	<#if CATEGORYLIST??>
        	<#list CATEGORYLIST as c>
        	<#if (c.courseTotal > 0)>
            <div class="sidelist">
                <div><h3><a href="/${c.abbr}/"><span class="fl">${c.name}</span><span class="arrow">&gt;</span></a></h3></div>
                <div class="kch-class">
                    <div class="list">
                    	<ul>
                        	<@createExam c />
                        </ul>
                    </div>
                </div>
            </div>
            </#if>
            </#list>
            </#if>
        </div>
        </div>
        <div class="nva fl">
        	<div class="list">
            	<ul>
                	<li><a href="#">首页</a></li>
                    <li><a href="#">试听课程</a></li>
                    <li><a href="#">我的课程</a></li>
                    <li><a href="#">我的订单</a></li>
                    <li><a href="#">我的收藏</a></li>
                    <li><a href="#">资料专区</a></li>
                </ul>
            </div>
        </div>
        <#--<div class="buy-cart"><a href="#" target="_blank">购物车<span>0</span></a></div>-->
        <div class="search fl">
        	<input name="" type="text" class="box" border="0">
            <div class="btn"><a href="#"></a></div>
        </div>
    </div>
</div>
</#macro>
<#macro createExam category>
	<#if category.exams??>
		<#list category.exams as exam>
			<li><a href="#" target="_blank" title="${exam.name}">${exam.name}</a></li>
		</#list>
	</#if>
	<#if category.children??>
		<#list category.children as child>
			<@createExam child/>
		</#list>
	</#if>
</#macro>
<#macro calculateCourseTotal categoryList>
	<#if categoryList??>
		<#assign total = 0/>
		<#list categoryList as c>
			<#assign total = total + c.courseTotal />
		</#list>
		${total}
	<#else>0
	</#if>
</#macro>
<#-- 左边 -->
<#macro left>
   <div class="pagevip-l fl yinying">
    	<div class="pagevip-l fl yinying">
    	<div class="vip-tit">${frontUser.user.account}<a href='<@s.url "/${abbr}/logout"/>'>退出</a></div>
        <ul class="pagevip-nav">
            <li id="myCourse" class="cur"><a href="<@s.url '/${abbr}/user/myCourse'/>">我的课程</a></li>
            <li id="myQuestion"><a href="<@s.url "/${abbr}/user/myQuestion"/>">我的问答</a></li>
            <li id="userInfo"><a href="<@s.url "/${abbr}/user/info"/>">个人资料</a></li>
        </ul>
    </div>
    </div>
</#macro>
<#-- 考试分类导航 -->
<#macro getNavigation category>
	<#if category.parent??>
		<@getNavigation category.parent/>
	</#if>
	<a href="<@s.url '/${abbr}/course/${FLAG}'/>?categoryId=${category.id}">${category.name}</a><#if !category.parent??> > </#if>
</#macro>