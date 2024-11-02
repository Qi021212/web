var content = this.document.getElementsByClassName("slide")[0]
var li = content.getElementsByTagName("li")
function fun (i, j){
    li[i].style.opacity = 1;
    li[j].style.opacity = 0;
    li[i+6].style.backgroundColor = "black";
    li[j+6].style.backgroundColor = "";
}
fun(0, 1)
var i = 0
function auto(){
    i++
    if(i>=6){
        i=0;
        fun(0,5);
    }
    else{
        fun(i, i-1);
    }
}
timer = this.setInterval(auto, 2500)
content.onmouseover = function(){
    clearInterval(timer);
}
content.onmouseout = function(){
    timer = setInterval(auto, 2500);
}
for(j=0;j<6;j++){
    li[j+6].index = j;
    li[j+6].onclick = function(){
        fun(this.index, i);
        i = this.index;
    }
}