use std::alloc::{alloc, dealloc, Layout};
use std::default::Default;
use std::fmt;
use std::ptr;
use std::slice;

struct Vector<T> {
    cap: usize,
    len: usize,
    ptr: *mut T,
}

impl<T> Vector<T> {
    const DEFAULT_CAPACITY: usize = 16;

    #[inline]
    pub fn new() -> Self {
        Self::with_capacity(Self::DEFAULT_CAPACITY)
    }

    #[inline]
    pub fn with_capacity(capacity: usize) -> Self {
        let ptr = Self::malloc(capacity);
        Self {
            cap: capacity,
            len: 0,
            ptr: ptr,
        }
    }

    #[inline]
    pub fn push(&mut self, v: T) {
        let len = self.len;
        if len < self.cap {
            unsafe { ptr::write(self.ptr.add(len), v); }
            self.len += 1;
            return
        }
        let new_cap = self.cap * 2;
        let dst = Self::malloc(new_cap);
        let src = self.ptr;
        unsafe {
            ptr::copy_nonoverlapping(src, dst, len);
            ptr::write(dst.add(len), v);
            Self::free(src, self.cap);
        }
        self.len += 1;
        self.ptr = dst;
        self.cap = new_cap;
    }

    #[inline]
    pub fn pop(&mut self) -> Option<T> {
        if self.len == 0 {
            return None
        }
        let v = unsafe { ptr::read(self.ptr.add(self.len-1) as *const T) };
        self.len -= 1;
        Some(v)
    }

    #[inline]
    pub fn shrink_to_fit(&mut self) {
        let cap = self.cap;
        let len = self.len;
        if cap == len {
            return
        }
        let src = self.ptr;
        let dst = Self::malloc(len);
        unsafe {
            ptr::copy_nonoverlapping(src, dst, len);
            Self::free(src, cap);
        }
        self.ptr = dst;
        self.cap = len;
    }

    #[inline]
    pub fn get(&self, idx: usize) -> Option<&T> {
        if idx >= self.len {
            return None
        }
        let v = unsafe { &*self.ptr.add(idx) };
        Some(v)
    }

    #[inline]
    pub fn get_mut(&mut self, idx: usize) -> Option<&mut T> {
        if idx >= self.len {
            return None
        }
        let v = unsafe { &mut *self.ptr.add(idx) };
        Some(v)
    }

    #[inline]
    pub const fn as_slice(&self) -> &[T] {
        unsafe { slice::from_raw_parts(self.as_ptr(), self.len) }
    }

    #[inline]
    pub const fn as_mut_slice(&mut self) -> &mut [T] {
        unsafe { slice::from_raw_parts_mut(self.as_mut_ptr(), self.len) }
    }

    #[inline]
    pub const fn as_ptr(&self) -> *const T {
        self.ptr as *const T
    }

    #[inline]
    pub const fn as_mut_ptr(&mut self) -> *mut T {
        self.ptr
    }

    #[inline]
    pub const fn len(&self) -> usize {
        self.len
    }

    #[inline]
    pub const fn is_empty(&self) -> bool {
        self.len == 0
    }

    #[inline]
    pub const fn capacity(&self) -> usize {
        self.cap
    }

    #[inline]
    pub const fn clear(&mut self) {
        self.len = 0;
    }

    #[inline]
    pub const fn iter(&self) -> VectorIter<T> {
        VectorIter {
            current: self.ptr,
            remaining: self.len,
            phantom: std::marker::PhantomData,
        }
    }

    #[inline]
    pub const fn iter_mut(&mut self) -> VectorIterMut<T> {
        VectorIterMut {
            current: self.ptr,
            remaining: self.len,
            phantom: std::marker::PhantomData,
        }
    }

    #[inline]
    fn malloc(n: usize) -> *mut T {
        let layout = Layout::array::<T>(n).expect("Invalid layout");
        let ptr = unsafe { alloc(layout) as *mut T };
        if ptr.is_null() {
            panic!("Could not allocate memory");
        }
        ptr
    }

    #[inline]
    unsafe fn free(ptr: *mut T, n: usize) {
        let layout = Layout::array::<T>(n).expect("Invalid layout");
        dealloc(ptr as *mut u8, layout);
    }
}

impl<T> Default for Vector<T> {
    fn default() -> Self {
        Self::new()
    }
}

impl<T> Drop for Vector<T> {
    fn drop(&mut self) {
        if !self.ptr.is_null() {
            unsafe { Self::free(self.ptr,  self.cap); }
        }
    }
}

impl<T: fmt::Debug> fmt::Debug for Vector<T> {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        f.debug_list().entries(self.iter()).finish()
    }
}

impl<T> AsRef<[T]> for Vector<T> {
    fn as_ref(&self) -> &[T] {
        self.as_slice()
    }
}

struct VectorIter<'a, T> {
    current: *const T,
    remaining: usize,
    phantom: std::marker::PhantomData<&'a T>,
}

impl<'a, T> Iterator for VectorIter<'a, T> {
    type Item = &'a T;

    fn next(&mut self) -> Option<Self::Item> {
        if self.remaining == 0 {
            return None;
        }

        let item: Self::Item;
        unsafe {
            item = &*self.current;
            self.current = self.current.add(1);
        }
        self.remaining -= 1;
        Some(item)
    }
}

impl<'a, T> IntoIterator for &'a Vector<T> {
    type Item = &'a T;
    type IntoIter = VectorIter<'a, T>;

    fn into_iter(self) -> Self::IntoIter {
        self.iter()
    }
}

struct VectorIterMut<'a, T> {
    current: *mut T,
    remaining: usize,
    phantom: std::marker::PhantomData<&'a T>,
}

impl<'a, T> Iterator for VectorIterMut<'a, T> {
    type Item = &'a mut T;

    fn next(&mut self) -> Option<Self::Item> {
        if self.remaining == 0 {
            return None;
        }

        let item: Self::Item;
        unsafe {
            item = &mut *self.current;
            self.current = self.current.add(1);
        }
        self.remaining -= 1;
        Some(item)
    }
}

impl<'a, T> IntoIterator for &'a mut Vector<T> {
    type Item = &'a mut T;
    type IntoIter = VectorIterMut<'a, T>;

    fn into_iter(self) -> Self::IntoIter {
        self.iter_mut()
    }
}


#[cfg(test)]
mod tests {
    use super::*;

    // TODO: more and better tests.
    #[test]
    fn works() {
        let cap = 2;
        let mut xs = Vector::<usize>::with_capacity(cap);
        assert_eq!(xs.pop(), None, "New vector must be empty");
        assert_eq!(xs.pop(), None, "pop on an empty vector must be idempotent");
        assert_eq!(xs.len(), 0, "New vector has length = 0");
        assert_eq!(xs.capacity(), cap, "New vector has requested capacity");

        xs.push(5);
        assert_eq!(xs.len(), 1, "Vector must have length = 1 after pushing one item to an empty vector");
        assert_eq!(xs.capacity(), cap, "Vector capacity must be unchanged after pushing N elements where N < cap");
        assert_eq!(xs.get(0), Some(5).as_ref(), "Vector must contain 5 after pushing 5");
        assert_eq!(xs.get(0), Some(5).as_ref(), "Vector::get must be idempotent");
        assert_eq!(xs.pop(), Some(5), "Vector::pop must return 5 after pushing 5");
        assert_eq!(xs.pop(), None, "Exhausted vector must be empty");
        assert_eq!(xs.pop(), None, "Vector::pop must be idempotent");
        assert_eq!(xs.len(), 0, "Exhausted vector must have length = 0");

        for i in 1..=(cap+1) {
            xs.push(i);
        }
        assert_eq!(xs.len(), cap+1, "Length must be N after pushing N elements");
        assert!(xs.capacity() > cap);
        for i in (1..=(cap+1)).rev() {
            assert_eq!(xs.pop(), Some(i), "Popped element must match the pushed one");
        }
                
        xs.clear();
        assert_eq!(xs.len(), 0, "Cleared vector must be empty");
        xs.clear();
        assert_eq!(xs.len(), 0, "Clear on an empty vector must be idempotent");

        xs.push(1);
        for i in xs.iter() {
            assert_eq!(*i, 1usize, "Iter must work");
        }
        xs.shrink_to_fit();
        assert_eq!(xs.capacity(), 1, "Vector::shrink_to_fit must shrink capacity to length");
    }
}
