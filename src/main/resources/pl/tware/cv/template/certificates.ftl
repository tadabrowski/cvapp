<table class="certificates-table">
  	<#list certificates as certificatesItem>
  		<tr>
  			<td>
  				${certificatesItem.year}</p>
  			</td>
  			<td>
  				${certificatesItem.name}</p>
  			</td>
  			<td>
  				${image.get(certificatesItem.img, "logo-medium")}</p>
  			</td>
  		</tr>  	  		  	  
	</#list>
</table>