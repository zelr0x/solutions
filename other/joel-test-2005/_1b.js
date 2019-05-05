const accumulate = (combiner, nullValue, arr) => (arr.length === 0) 
    ? nullValue 
    : combiner(arr.shift(), accumulate(combiner, nullValue, arr));

const sumOfSquares = (arr) => accumulate((x, y) => x * x + y, 0, arr);

sumOfSquares([1,2,3,4,5]);
