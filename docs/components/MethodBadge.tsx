import { cn } from "@/lib/utils";

const methodColors: Record<string, string> = {
	GET: "bg-[#5DFDCB]/15 text-[#5DFDCB] border-[#5DFDCB]/30",
	POST: "bg-[#B07AFF]/15 text-[#B07AFF] border-[#B07AFF]/30",
	PUT: "bg-[#FFB07A]/15 text-[#FFB07A] border-[#FFB07A]/30",
	PATCH: "bg-[#FFB07A]/15 text-[#FFB07A] border-[#FFB07A]/30",
	DELETE: "bg-[#FF6B9D]/15 text-[#FF6B9D] border-[#FF6B9D]/30",
};

export default function MethodBadge({ method }: { method: string }) {
	return (
		<span
			className={cn(
				"inline-flex justify-center font-mono text-xs font-semibold w-[4.5rem] py-1 rounded-md border shrink-0",
				methodColors[method] || "",
			)}
		>
			{method}
		</span>
	);
}
