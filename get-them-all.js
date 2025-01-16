export function getArchitects(){
    return [
        document.querySelectorAll('body a'),
        document.querySelectorAll('body span')
    ];
}
export function getClassical(){
    return [
        document.querySelectorAll('a.classical'),
        document.querySelectorAll('a:not(.classical)')
    ];
}
export function getActive(){
    return [
        document.querySelectorAll('a.active'),
        document.querySelectorAll('a:not(.active)')
    ];
}
export function getBonannoPisano(){
    return [
        document.getElementById('BonannoPisano'),
        document.querySelectorAll('a.classical.active')
      ];
}