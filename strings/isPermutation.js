// Given two strings, find whether they are permutations of each other.
const isPermutation = (...arr) => {
  if (arr.length != 2) {
    throw new Error('Array of two strings expected')
  }
  return arr[0].length == arr[1].length &&
    arr[1].toLowerCase()
      .split('')
      .every(x => arr[0].toLowerCase().includes(x))
}

console.log(isPermutation('hello', 'hey'))
console.log(isPermutation('hAlo', 'hOla'))
