function citiesOnly(arr){return arr.map(n=>n.city)};
function upperCasingStates(arr){return arr.map(n=>n.split(' ').map(k=>k.charAt(0).toUpperCase()+k.substr(1,k.length).toLowerCase()).toString().replace(/,/g,' '))};
function fahrenheitToCelsius(arr){return arr.map(n=>(Math.floor((Number(n.slice(0,-2).toString())-32)*5/9)).toString()+'°C')};
//find out why n.slice(0,-2) works yet n.match(/0-9,-,.) doesnt.Cant elewa why
function trimTemp(arr){arr.map((n)=>{ 
    n['temperature']=n['temperature'].replace(/\s/g,'')})
            return arr};
function tempForecasts(arr){return  arr.map(k=>{
    let cel=Math.floor((Number(k.temperature.slice(0,-2).toString())-32)*5/9).toString()+'°Celsius';
    let stateslice=k.state.split(' ').map(state=>state.charAt(0).toUpperCase()+state.substr(1,state.length).toLowerCase())
    k.state=stateslice.concat().toString().replace(',',' ')
    console.log(k.state)
    return `${cel} in ${k.city}, ${k.state}`
})};
 console.log(
    tempForecasts([
    {
      city: 'Pasadena',
      temperature: ' 101 °F',
      state: 'california north',
      region: 'West',
    },
 ])
)
//fahrenheitToCelsius(['1014°F', '1250.6°F', '169°F','405.6','-439.4','709.8','-642.2']),Math.ceil(-3.8))//['38°Celsius in Pasadena, California'] 
// '30°C',*33.8, 1014
// '37°C',     1250.6
// '5°C',      169
// '12°C',     405.6
// '-13°C',    -439.4
// '21°C',      709.8
// '-19°C',     -642.2