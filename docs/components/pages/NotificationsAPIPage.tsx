"use client";

import { useLocale, useTranslations } from "next-intl";
import ApiEndpoint from "@/components/ApiEndpoint";
import Breadcrumb from "@/components/Breadcrumb";
import CodeBlock from "@/components/CodeBlock";
import PageNav from "@/components/PageNav";
import ParamTable from "@/components/ParamTable";

export default function NotificationsAPIPage() {
	const locale = useLocale();
	const t = useTranslations("pages");
	const ta = useTranslations("api");

	return (
		<div className="space-y-8">
			<Breadcrumb
				items={[
					{ label: "Develop", href: `/${locale}/develop/getting-started` },
					{ label: "API Reference" },
					{ label: t("notifications_api_title").replace(" API", "").replace("API ", "") },
				]}
			/>

			<div>
				<h1 className="text-3xl font-bold text-[#F0EDF5] mb-2">{t("notifications_api_title")}</h1>
				<p className="text-[#9B8FB8] leading-relaxed">{t("notifications_api_desc")}</p>
			</div>

			{/* GET /api/v1/notifications/user/{userId} */}
			<div className="space-y-4">
				<h2 id="get-notifications" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_notifications")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/notifications/user/{userId}"
					description="Returns paginated notifications for a user, newest first"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-notifications-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "userId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the user whose notifications to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-notifications-query"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("query_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "page",
										type: "integer",
										required: false,
										description: "Page number (zero-based). Default: 0",
									},
									{
										name: "size",
										type: "integer",
										required: false,
										description: "Number of items per page. Default: 20",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-notifications-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl "http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890?page=0&size=10"`,
									},
									{
										label: "JavaScript",
										code: `const userId = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/notifications/user/\${userId}?page=0&size=10\`
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    params={"page": 0, "size": 10},
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-notifications-response"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("response")}
							</h3>
							<CodeBlock
								response
								tabs={[
									{
										label: "JSON",
										code: `{
  "success": true,
  "message": null,
  "data": {
    "content": [
      {
        "id": "n1a2b3c4-d5e6-7890-abcd-ef1234567890",
        "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "type": "LARGE_TRANSACTION",
        "title": "Large Transaction Detected",
        "message": "A transaction of $2,500.00 was recorded on your Main Checking account",
        "channel": "IN_APP",
        "isRead": false,
        "createdAt": "2026-03-15T14:30:00Z"
      },
      {
        "id": "n2b3c4d5-e6f7-8901-bcde-f12345678901",
        "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "type": "BUDGET_ALERT",
        "title": "Budget Limit Approaching",
        "message": "You have used 90% of your Food & Dining budget this month",
        "channel": "IN_APP",
        "isRead": true,
        "createdAt": "2026-03-14T09:00:00Z"
      }
    ],
    "totalElements": 15,
    "totalPages": 2,
    "page": 0,
    "size": 10
  },
  "timestamp": "2026-03-15T14:35:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/notifications/user/{userId}/unread-count */}
			<div className="space-y-4">
				<h2 id="get-unread-count" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_unread_count")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/notifications/user/{userId}/unread-count"
					description="Returns the number of unread notifications for a user"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-unread-count-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "userId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the user",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-unread-count-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890/unread-count`,
									},
									{
										label: "JavaScript",
										code: `const userId = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/notifications/user/\${userId}/unread-count\`
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890/unread-count"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-unread-count-response"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("response")}
							</h3>
							<CodeBlock
								response
								tabs={[
									{
										label: "JSON",
										code: `{
  "success": true,
  "message": null,
  "data": 5,
  "timestamp": "2026-03-15T14:36:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* PUT /api/v1/notifications/{id}/read */}
			<div className="space-y-4">
				<h2 id="mark-as-read" className="text-xl font-semibold text-[#F0EDF5]">
					{t("mark_as_read")}
				</h2>
				<ApiEndpoint
					method="PUT"
					path="/api/v1/notifications/{id}/read"
					description="Marks a single notification as read"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="mark-as-read-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "id",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the notification to mark as read",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="mark-as-read-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X PUT http://localhost:8080/api/v1/notifications/n1a2b3c4-d5e6-7890-abcd-ef1234567890/read`,
									},
									{
										label: "JavaScript",
										code: `const notificationId = "n1a2b3c4-d5e6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/notifications/\${notificationId}/read\`,
  { method: "PUT" }
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.put(
    "http://localhost:8080/api/v1/notifications/n1a2b3c4-d5e6-7890-abcd-ef1234567890/read"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="mark-as-read-response"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("response")}
							</h3>
							<CodeBlock
								response
								tabs={[
									{
										label: "JSON",
										code: `{
  "success": true,
  "message": "Notification marked as read",
  "data": null,
  "timestamp": "2026-03-15T14:37:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* PUT /api/v1/notifications/user/{userId}/read-all */}
			<div className="space-y-4">
				<h2 id="mark-all-as-read" className="text-xl font-semibold text-[#F0EDF5]">
					{t("mark_all_as_read")}
				</h2>
				<ApiEndpoint
					method="PUT"
					path="/api/v1/notifications/user/{userId}/read-all"
					description="Marks all unread notifications for a user as read"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="mark-all-as-read-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "userId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the user whose notifications to mark as read",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="mark-all-as-read-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X PUT http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890/read-all`,
									},
									{
										label: "JavaScript",
										code: `const userId = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/notifications/user/\${userId}/read-all\`,
  { method: "PUT" }
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.put(
    "http://localhost:8080/api/v1/notifications/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890/read-all"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="mark-all-as-read-response"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("response")}
							</h3>
							<CodeBlock
								response
								tabs={[
									{
										label: "JSON",
										code: `{
  "success": true,
  "message": "All notifications marked as read",
  "data": null,
  "timestamp": "2026-03-15T14:38:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			<PageNav
				prev={{
					label: "Categories API",
					href: `/${locale}/develop/api/categories`,
				}}
				next={{
					label: "Budgets API",
					href: `/${locale}/develop/api/budgets`,
				}}
			/>
		</div>
	);
}
