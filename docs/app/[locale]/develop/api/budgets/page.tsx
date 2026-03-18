import { setRequestLocale } from "next-intl/server";
import BudgetsAPIContent from "@/components/pages/BudgetsAPIPage";

export default async function BudgetsAPI({ params }: { params: Promise<{ locale: string }> }) {
	const { locale } = await params;
	setRequestLocale(locale);
	return <BudgetsAPIContent />;
}
