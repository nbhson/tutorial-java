// ✗ vi phạm kinh điển: Vuong extends ChuNhat nhưng phá rule
class ChuNhat { void setW(int w); void setH(int h); }
class Vuong extends ChuNhat {  // setW đồng thời setH → phá kỳ vọng của caller
}

// ✅ tách interface riêng nếu hành vi khác nhau
interface Hinh { double dienTich(); }
