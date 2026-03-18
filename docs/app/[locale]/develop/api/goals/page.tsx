import { setRequestLocale } from "next-intl/server";
import GoalsAPIContent from "@/components/pages/GoalsAPIPage";

export default async function GoalsAPI({ params }: { params: Promise<{ locale: string }> }) {
	const { locale } = await params;
	setRequestLocale(locale);
	return <GoalsAPIContent />;
}
