import { setRequestLocale } from "next-intl/server";
import TransactionsAPIContent from "@/components/pages/TransactionsAPIPage";

export default async function TransactionsAPI({
  params,
}: {
  params: Promise<{ locale: string }>;
}) {
  const { locale } = await params;
  setRequestLocale(locale);
  return <TransactionsAPIContent />;
}
