import { useEffect, useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import { User } from "../admin/landing/types";

export function useCurrentUser(): { user: User | null, loading: boolean } {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/admin/login");
      return;
    }
    axios.get("https://chic-integrity-production.up.railway.app/api/users/me", {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setUser(res.data))
      .catch(() => router.push("/admin/login"))
      .finally(() => setLoading(false));
  }, [router]);

  return { user, loading };
}