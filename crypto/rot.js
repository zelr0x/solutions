// 
const rot = (str, shift=13, aStart='A', aSize=26) => {
  const res = [];
  aStart = aStart.charCodeAt(0);
  const aEnd = aStart + aSize - 1;
  for (let i = 0, len = str.length; i < len; i++) {
    const cp = str.charCodeAt(i);
    cp >= aStart && cp <= aEnd
      ? res.push(String.fromCharCode((cp - aStart + shift) % aSize + aStart))
      : res.push(str[i]);
  }
  return res.join('');
}

console.log(rot('LBH QVQ VG!')); // YOU DID IT!
console.log(rot('lbh qvq vg!', 13, 'a')); // you did it!
