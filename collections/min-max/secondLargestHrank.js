function getSecondLargest(nums) {
    const max = Math.max(...nums)
    return Math.max(...nums.filter(x => x !== max))
}
