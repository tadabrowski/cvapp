<table class="technologies-table">
  	<#list technologies as technologiesItem>
  		<#if technologiesItem_index%8 = 0>
			<tr>
  		</#if>
  	
		<td>
		<div class="technologies-image">
			<#if technologiesItem.img??>
				${image.get(technologiesItem.img, "logo-medium")}
			<#else>
				<p class="technologies-fake-logo">${technologiesItem.description}</p>
			</#if>
		</div>
		<div class="technologies-description">			
			<p>${technologiesItem.description}</p>
		</div>
		</td>
  		
  		<#if technologiesItem_index%8 = 7>
			</tr>
  		</#if>  	  		  	  
	</#list>
</table>