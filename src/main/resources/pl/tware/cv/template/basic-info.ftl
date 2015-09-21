  	<table>
      <tr>
         <td rowspan='10'>
            ${image.get('author', "photo")}
         </td>
	  </tr>
	  <tr>
         <td colspan='2'>${nameValue}</td>
      </tr>
      <tr>
         <td>${dateAndPlaceOfBornLabel}</td>
         <td>${dateAndPlaceOfBornValue}</td>
      </tr>
	  <tr>
         <td>${addressLabel}</td>
         <td>${addressValue}</td>
      </tr>
	  <tr>
         <td>${emailLabel}</td>
         <td>${emailValue}</td>
      </tr>
	  <tr>
         <td>${phoneNumberLabel}</td>
         <td>${phoneNumberValue}</td>
      </tr>
      <tr><td><div style='margin: 20px 0px;'>
    	  <#list services as servicesItem>
    	  	<a href='${servicesItem.url}' target='_blank' style='float: left;'>
     	    	${image.get(servicesItem.img, "logo-small")}
     	    </a>
   		  </#list>
      </div></td></tr>
   </table>