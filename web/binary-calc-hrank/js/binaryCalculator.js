let calc = {}
;[].map.call(document.querySelectorAll('button.btn'),
        x => calc[x.id] = x)
calc.res = document.getElementById('res')

const print = (x) => calc.res.innerHTML += x
const printLn = (x) => calc.res.innerHTML = x
const dec2bin = (x) => (x >>> 0).toString(2)
const bin2dec = (x) => parseInt(x, 2)

Object.keys(calc).map(i => calc[i].onclick = () =>
        print(calc[i].innerHTML))

calc.btnClr.onclick = () => printLn('')

calc.btnEql.onclick = () => {    
    if (calc.res.innerHTML == 0) {
        printLn(0)
    }
    else {
        let [left, op, right] = calc.res.innerHTML
                .split(/([+*-/])/)
                .map(x => x.trim())
        printLn(op === undefined 
                ? left
                : dec2bin(eval(bin2dec(left) + op + bin2dec(right))))
    }
}