import { setRequestLocale } from "next-intl/server";
import NotificationsAPIContent from "@/components/pages/NotificationsAPIPage";

export default async function NotificationsAPI({
	params,
}: {
	params: Promise<{ locale: string }>;
}) {
	const { locale } = await params;
	setRequestLocale(locale);
	return <NotificationsAPIContent />;
}
