import { Navigate, useLocation } from "react-router-dom";
import { useAuthStore } from "@/features/auth/store/authStore";
import { ReactNode } from "react";

interface RouteMiddlewareProps {
  children: ReactNode;
  /**
   * "guest": Only accessible to unauthenticated users (e.g. Login, Register)
   * "auth": Only accessible to authenticated users (e.g. Profile, Admin)
   */
  type: "guest" | "auth";
}

export function RouteMiddleware({ children, type }: RouteMiddlewareProps) {
  const user = useAuthStore((state) => state.user);
  const location = useLocation();

  if (type === "guest" && user) {
    return <Navigate to="/" replace />;
  }

  if (type === "auth" && !user) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
}
