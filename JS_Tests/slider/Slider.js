function Slider(parentTag, imageUrls, size ,deltaTime ,changeTime){
	
	this.currentIndex = 0;
	this.lastUpdate = 0;
	this.imageUrls = imageUrls;
	this.length = imageUrls.length;
	this.deltaTime = deltaTime;
	this.changeTime = changeTime;
	
	//init main
	this.main = document.createElement("div");
	this.main.style.width = size.width+"px";
	this.main.style.height = size.height+"px";
	
	this.elements = [];
	
	this.createSlide = function(imageUrl,size){
		var tmp = document.createElement("div");
		tmp.style.position = "absolute";
		tmp.style.width = size.width+"px";
		tmp.style.height = size.height+"px";
		tmp.style.display = "none";
		tmp.style.backgroundImage = "url('"+imageUrl+"')";
		return tmp;
	};
	
	//init slides
	for(var i = 0;i < imageUrls.length;i++){
		var slide =  this.createSlide(imageUrls[i],size);
		this.elements.push(slide);
		this.main.appendChild(slide);
	}
	parentTag.appendChild(this.main);
	
	
	this.getNextIndex = function(){
		var nI = this.currentIndex + 1;
		if(nI >= this.length) nI = 0;
		return nI;
	};
	
	//update
	this.update = function(obj){
		
		if((timestamp() - obj.lastUpdate)/1000 > obj.deltaTime){
			obj.lastUpdate = timestamp();
			
			
			var next = obj.getNextIndex();
			
			obj.elements[obj.currentIndex].style.zIndex = 6;
			obj.elements[next].style.zIndex = 5;
			obj.elements[next].style.display = "block";
			
			
			$(obj.elements[obj.currentIndex]).fadeOut(obj.changeTime*1000);
			obj.currentIndex = next;
			
		}
		
		setTimeout(function(){obj.update(obj);},100);
	};
	
	
	this.elements[this.currentIndex].style.display = "block";
	this.lastUpdate = timestamp();
	this.update(this);
	
}

/*Use

new Slider(javascriptDOMObject, linksArray, (width:0,height:0),t1,t2)
*/
