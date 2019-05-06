// Pig Latin takes the first consonant (or consonant cluster) of an English word,
// moves it to the end of the word and suffixes an "ay".
// If a word begins with a vowel you just add "way" to the end.
function translatePigLatin(str) {
  const len = str.length;
  if (len < 1) return str;

  const consonantSuffix = "ay"; 
  const vowelSuffix = "way";

  const firstVowelIndex = str.search(/[eaiou]/i);
  if (firstVowelIndex === -1) return str + consonantSuffix;
  if (firstVowelIndex === 0) return str + vowelSuffix;
  return str.slice(firstVowelIndex) +
    str.slice(0, firstVowelIndex) + 
    consonantSuffix;
}

console.log(translatePigLatin("california")); // "aliforniacay" expected
