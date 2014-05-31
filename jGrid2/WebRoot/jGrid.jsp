<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title>My First Grid</title>

		<link rel="stylesheet" type="text/css" media="screen"
			href="css/jquery-ui.min.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/ui.multiselect.css" />

		<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	font-size: 75%;
}
</style>
		<script src="js/jquery.js" type="text/javascript"></script>
		<script src="js/grid.locale-en.js" type="text/javascript"></script>
		<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

		<script type="text/javascript">

	$(function() {
		$("#list").jqGrid( {
			url : "data.json",
			datatype : "json",
			mtype : "GET",
			colNames : [ "Inv No", "Date", "Amount", "Tax", "Total", "Notes" ],
			colModel : [ {
				name : "invid",
				width : 75
			}, {
				name : "invdate",
				width : 120
			}, {
				name : "amount",
				width : 110,
				align : "right"
			}, {
				name : "tax",
				width : 110,
				align : "right"
			}, {
				name : "total",
				width : 110,
				align : "right"
			}, {
				name : "note",
				width : 170,
				sortable : false
			} ],
			pager : "#pager",
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			sortname : "invid",
			sortorder : "asc",
			viewrecords : true,
			gridview : true,
			autoencode : true,
			caption : "My first grid"
		});
	});
</script>

	</head>
	<body>
		<table id="list">
			<tr>
				<td></td>
			</tr>
		</table>
		<div id="pager"></div>
	</body>
</html>