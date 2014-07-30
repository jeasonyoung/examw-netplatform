 
 <#include "common/layout.ftl"/>
 <@layout_list title="hello word">
	 <h1>hello ${name} </h1>
	 <p>
	<@s.url "  ok"/> <br/>
	</p>
	<code>
	<@s.url  "\\${name}"/> 
	</code>
</@layout_list>