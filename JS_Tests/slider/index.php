<html>
	<head>
		<title>Slider</title>
		<script src="jquery-3.1.1.min.js"></script>
		<script src="functions.js"></script>
		<script src="Slider.js"></script>
		<script src="script.js"></script>
		
	</head>
	<body>
		<div id="slider1">
			<script>
				var slider1 = new Slider(
					document.getElementById("slider1"),
					["bg1.jpg","bg2.jpg","bg3.jpg"],
					{width:1280,height:720},
					3,1
				);
				
				
				
			</script>
		</div>
		<div id="slider2">
			<script>
				var slider2 = new Slider(
					document.getElementById("slider2"),
					["bg1_1.jpg","bg1_2.jpg","bg1_3.jpg","bg1_4.jpg","bg1_5.jpg","bg1_6.jpg","bg1_7.jpg","bg1_8.jpg"],
					{width:800,height:600},
					1.5,0.5
				);
				slider2.main.onclick = function(){
					window.location = slider2.imageUrls[slider2.currentIndex];
				};
			</script>
		</div>
	</body>
</html>