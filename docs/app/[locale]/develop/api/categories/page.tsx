import { setRequestLocale } from "next-intl/server";
import CategoriesAPIContent from "@/components/pages/CategoriesAPIPage";

export default async function CategoriesAPI({
  params,
}: {
  params: Promise<{ locale: string }>;
}) {
  const { locale } = await params;
  setRequestLocale(locale);
  return <CategoriesAPIContent />;
}
