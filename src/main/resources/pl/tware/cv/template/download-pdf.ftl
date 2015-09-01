<div class="download-pdf">
	<#list languages?keys as languagesItem>	
		<p><a href="/get-pdf-file?language=${languagesItem}" target="_blank">
			${clickToDownload[languagesItem.name()]}
		</p></p>
	</#list>
</div>