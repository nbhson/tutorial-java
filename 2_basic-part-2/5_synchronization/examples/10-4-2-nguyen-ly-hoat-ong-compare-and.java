// ✗ atomic nhung van loi!
if (counter.get() < MAX) {
    counter.increment(); // giua 2 dong, thread khac da tang len
}

// ✅ phai synchronized cho ca logic
synchronized (lock) {
    if (count < MAX) count.increment();
}
