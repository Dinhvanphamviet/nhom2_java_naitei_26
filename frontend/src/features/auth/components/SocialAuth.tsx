import { Button } from "@/components/ui/button";
import { GoogleIcon, FacebookIcon } from "@/components/common/icons";

export function SocialAuth() {
  return (
    <div className="grid grid-cols-2 gap-3">
      <Button variant="outline" className="w-full py-5 text-slate-700 bg-white hover:bg-slate-50 border-slate-200 rounded-xl" type="button">
        <GoogleIcon />
        Google
      </Button>
      <Button variant="outline" className="w-full py-5 text-slate-700 bg-white hover:bg-slate-50 border-slate-200 rounded-xl" type="button">
        <FacebookIcon />
        Facebook
      </Button>
    </div>
  );
}
