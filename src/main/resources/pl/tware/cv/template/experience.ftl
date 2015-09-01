<table class="experience-table">
  	<#list experience as experienceItem>  	
  		<tr class="experience-first-level">
         	<td>${experienceItem.date}</td>
         	<td>${experienceItem.companyName}</td>
        </tr>                 	
        <#list experienceItem.projects as projectItem>
        	<tr class="experience-second-level experience-second-level-first">
        		<td>${projectLabel}</td>
				<td>${projectItem.project}</td>
			</tr>
			<tr class="experience-second-level">
        		<td>${clientLabel}</td>
				<td>${projectItem.client}</td>
			</tr>
			<tr class="experience-second-level">
        		<td>${descriptionLabel}</td>
				<td class="experience-description">${projectItem.description}</td>
			</tr>
			<tr class="experience-second-level">
        		<td>${technologiesLabel}</td>
				<td>${projectItem.technologies}</td>
			</tr>
			<tr class="experience-second-level">
        		<td>${roleLabel}</td>
				<td>${projectItem.role}</td>
			</tr>
         </#list>  	  	
	</#list>
</table>