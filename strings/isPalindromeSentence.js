// Check whether a sentence is a palindrome.
function isPalindrome(str) {
  str = str.replace(/\W+|_/g, '').toLowerCase();
  for (let i = 0, len = str.length, mid = len / 2; i < mid; i++) {
    if (str[i] != str[len - i - 1]) return false;
  }
  return true;
}

isPalindrome("A man, a plan, a canal. Panama");
