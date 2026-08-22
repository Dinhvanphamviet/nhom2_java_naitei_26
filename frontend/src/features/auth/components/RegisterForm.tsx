import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Eye, EyeOff, Loader2 } from "lucide-react";
import { useRegisterForm } from "../hooks/useRegisterForm";

export function RegisterForm() {
  const {
    form, update,
    errors,
    loading,
    showPw, setShowPw,
    handleSubmit
  } = useRegisterForm();

  const fields = [
    { key: "username", label: "Tên đăng nhập", placeholder: "nguyenvan123", type: "text" },
    { key: "fullName", label: "Họ và tên", placeholder: "Nguyễn Văn A", type: "text" },
    { key: "email", label: "Địa chỉ Email", placeholder: "email@vidu.com", type: "email" },
    { key: "phone", label: "Số điện thoại", placeholder: "0912345678", type: "tel" },
  ];

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      {fields.map((f) => (
        <div key={f.key} className="space-y-2">
          <Label className="text-slate-700 font-semibold text-[13px] tracking-wide uppercase">{f.label} <span className="text-red-500">*</span></Label>
          <Input
            type={f.type}
            value={form[f.key as keyof typeof form]}
            onChange={(e) => update(f.key)(e.target.value)}
            placeholder={f.placeholder}
            className={`rounded-xl py-5 text-base ${errors[f.key] ? "border-red-400 focus-visible:ring-red-400" : ""}`}
          />
          {errors[f.key] && <div className="text-xs text-red-500">{errors[f.key]}</div>}
        </div>
      ))}

      {/* Password */}
      <div className="space-y-2">
        <Label className="text-slate-700 font-semibold text-[13px] tracking-wide uppercase">Mật khẩu <span className="text-red-500">*</span></Label>
        <div className="relative">
          <Input 
            type={showPw ? "text" : "password"} 
            value={form.password} 
            onChange={(e) => update("password")(e.target.value)} 
            placeholder="Tối thiểu 8 ký tự" 
            className={`pr-10 py-5 text-base rounded-xl ${errors.password ? "border-red-400 focus-visible:ring-red-400" : ""}`} 
          />
          <button 
            type="button" 
            onClick={() => setShowPw(!showPw)} 
            className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400 hover:text-slate-600"
          >
            {showPw ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
          </button>
        </div>
        {errors.password && <div className="text-xs text-red-500">{errors.password}</div>}
      </div>

      {/* Confirm Password */}
      <div className="space-y-2">
        <Label className="text-slate-700 font-semibold text-[13px] tracking-wide uppercase">Xác nhận mật khẩu <span className="text-red-500">*</span></Label>
        <Input 
          type="password" 
          value={form.confirm} 
          onChange={(e) => update("confirm")(e.target.value)} 
          placeholder="••••••••" 
          className={`py-5 text-base rounded-xl ${errors.confirm ? "border-red-400 focus-visible:ring-red-400" : ""}`} 
        />
        {errors.confirm && <div className="text-xs text-red-500">{errors.confirm}</div>}
      </div>

      <Button
        type="submit"
        disabled={loading}
        className="w-full py-6 rounded-xl mt-2 bg-gradient-to-r from-brand to-brand-dark hover:from-brand-dark hover:to-brand-darker text-base font-bold shadow-md text-white border-0"
      >
        {loading && <Loader2 className="animate-spin w-4 h-4 mr-2" />}
        {loading ? "Đang tạo tài khoản..." : "Tạo tài khoản"}
      </Button>
    </form>
  );
}
