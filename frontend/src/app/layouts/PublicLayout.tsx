import { Outlet } from "react-router-dom";
import { Navbar } from "@/components/layout/Navbar";

export default function PublicLayout() {
  return (
    <div className="min-h-screen bg-background flex flex-col">
      <Navbar />
      <main className="flex-1">
        <Outlet />
      </main>
      {/* Footer can be added here later */}
    </div>
  );
}
