let util={};
util.trimQuote = function(s){
    if(s.startsWith('"')){
        s = s.substring(1,s.length);
    }
    if(s.endsWith('"')){
        s = s.substring(0,s.length-1);
    }
    return s;
}

export default util;