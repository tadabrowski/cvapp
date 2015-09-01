<table>
  	<#list education as educationItem>
  		<tr>
         	<td>${educationItem.years}</td>
         	<td>
         	<#list educationItem.description as descriptionItem>         
         		<p>${descriptionItem}</p>
         	</#list>
         	</td>
        	<td>${image.get(educationItem.img, "logo-medium")}</td>
	  	</tr>  	  	
	</#list>
</table>