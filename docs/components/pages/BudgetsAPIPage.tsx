"use client";

import { useLocale, useTranslations } from "next-intl";
import ApiEndpoint from "@/components/ApiEndpoint";
import Breadcrumb from "@/components/Breadcrumb";
import CodeBlock from "@/components/CodeBlock";
import PageNav from "@/components/PageNav";
import ParamTable from "@/components/ParamTable";

export default function BudgetsAPIPage() {
	const locale = useLocale();
	const t = useTranslations("pages");
	const ta = useTranslations("api");

	return (
		<div className="space-y-8">
			<Breadcrumb
				items={[
					{ label: "Develop", href: `/${locale}/develop/getting-started` },
					{ label: "API Reference" },
					{ label: t("budgets_api_title").replace(" API", "").replace("API ", "") },
				]}
			/>

			<div>
				<h1 className="text-3xl font-bold text-[#F0EDF5] mb-2">{t("budgets_api_title")}</h1>
				<p className="text-[#9B8FB8] leading-relaxed">{t("budgets_api_desc")}</p>
			</div>

			{/* POST /api/v1/budgets */}
			<div className="space-y-4">
				<h2 id="create-budget" className="text-xl font-semibold text-[#F0EDF5]">
					{t("create_budget")}
				</h2>
				<ApiEndpoint
					method="POST"
					path="/api/v1/budgets"
					description="Creates a new budget with line items. The totalAmount is auto-calculated from the sum of item allocations."
				>
					<div className="space-y-6">
						<div>
							<h3
								id="create-budget-body"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("request_body")}
							</h3>
							<ParamTable
								params={[
									{
										name: "userId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the user who owns the budget",
									},
									{
										name: "name",
										type: "string",
										required: true,
										description: "A descriptive name for the budget",
									},
									{
										name: "periodType",
										type: "string",
										required: true,
										description: "Budget period: MONTHLY, QUARTERLY, YEARLY, or CUSTOM",
									},
									{
										name: "startDate",
										type: "string (date)",
										required: true,
										description: "Start date of the budget period (ISO 8601)",
									},
									{
										name: "endDate",
										type: "string (date)",
										required: true,
										description: "End date of the budget period (ISO 8601)",
									},
									{
										name: "items",
										type: "array",
										required: true,
										description:
											"Budget line items: [{categoryId, allocatedAmount, alertThresholdPct}]",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-budget-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X POST http://localhost:8080/api/v1/budgets \\
  -H "Content-Type: application/json" \\
  -d '{
    "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "March 2026 Budget",
    "periodType": "MONTHLY",
    "startDate": "2026-03-01",
    "endDate": "2026-03-31",
    "items": [
      { "categoryId": "cat-food-0001", "allocatedAmount": 500.00, "alertThresholdPct": 80 },
      { "categoryId": "cat-rent-0002", "allocatedAmount": 1500.00, "alertThresholdPct": 95 }
    ]
  }'`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch("http://localhost:8080/api/v1/budgets", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({
    userId: "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    name: "March 2026 Budget",
    periodType: "MONTHLY",
    startDate: "2026-03-01",
    endDate: "2026-03-31",
    items: [
      { categoryId: "cat-food-0001", allocatedAmount: 500.00, alertThresholdPct: 80 },
      { categoryId: "cat-rent-0002", allocatedAmount: 1500.00, alertThresholdPct: 95 },
    ],
  }),
});

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.post(
    "http://localhost:8080/api/v1/budgets",
    json={
        "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "name": "March 2026 Budget",
        "periodType": "MONTHLY",
        "startDate": "2026-03-01",
        "endDate": "2026-03-31",
        "items": [
            {"categoryId": "cat-food-0001", "allocatedAmount": 500.00, "alertThresholdPct": 80},
            {"categoryId": "cat-rent-0002", "allocatedAmount": 1500.00, "alertThresholdPct": 95},
        ],
    },
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-budget-response"
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
  "message": "Budget created successfully",
  "data": {
    "id": "b1a2b3c4-d5e6-7890-abcd-ef1234567890",
    "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "March 2026 Budget",
    "periodType": "MONTHLY",
    "startDate": "2026-03-01",
    "endDate": "2026-03-31",
    "totalAmount": 2000.00,
    "status": "ACTIVE",
    "items": [
      {
        "id": "bi-0001-aaaa-bbbb-cccc-dddddddddddd",
        "categoryId": "cat-food-0001",
        "allocatedAmount": 500.00,
        "spentAmount": 0.00,
        "alertThresholdPct": 80
      },
      {
        "id": "bi-0002-aaaa-bbbb-cccc-dddddddddddd",
        "categoryId": "cat-rent-0002",
        "allocatedAmount": 1500.00,
        "spentAmount": 0.00,
        "alertThresholdPct": 95
      }
    ],
    "createdAt": "2026-03-18T10:00:00Z"
  },
  "timestamp": "2026-03-18T10:00:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/budgets/{budgetId} */}
			<div className="space-y-4">
				<h2 id="get-budget-by-id" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_budget_by_id")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/budgets/{budgetId}"
					description="Returns a single budget with all its line items"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-budget-by-id-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "budgetId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the budget to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-budget-by-id-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/budgets/b1a2b3c4-d5e6-7890-abcd-ef1234567890`,
									},
									{
										label: "JavaScript",
										code: `const budgetId = "b1a2b3c4-d5e6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/budgets/\${budgetId}\`
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/budgets/b1a2b3c4-d5e6-7890-abcd-ef1234567890"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-budget-by-id-response"
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
    "id": "b1a2b3c4-d5e6-7890-abcd-ef1234567890",
    "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "March 2026 Budget",
    "periodType": "MONTHLY",
    "startDate": "2026-03-01",
    "endDate": "2026-03-31",
    "totalAmount": 2000.00,
    "status": "ACTIVE",
    "items": [
      {
        "id": "bi-0001-aaaa-bbbb-cccc-dddddddddddd",
        "categoryId": "cat-food-0001",
        "allocatedAmount": 500.00,
        "spentAmount": 125.50,
        "alertThresholdPct": 80
      },
      {
        "id": "bi-0002-aaaa-bbbb-cccc-dddddddddddd",
        "categoryId": "cat-rent-0002",
        "allocatedAmount": 1500.00,
        "spentAmount": 1500.00,
        "alertThresholdPct": 95
      }
    ],
    "createdAt": "2026-03-18T10:00:00Z"
  },
  "timestamp": "2026-03-18T10:05:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/budgets/user/{userId} */}
			<div className="space-y-4">
				<h2 id="get-budgets-by-user" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_budgets_by_user")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/budgets/user/{userId}"
					description="Returns paginated budgets for a user"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-budgets-by-user-params"
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
										description: "The UUID of the user whose budgets to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-budgets-by-user-query"
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
								id="get-budgets-by-user-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl "http://localhost:8080/api/v1/budgets/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890?page=0&size=20"`,
									},
									{
										label: "JavaScript",
										code: `const userId = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/budgets/user/\${userId}?page=0&size=20\`
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/budgets/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    params={"page": 0, "size": 20},
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-budgets-by-user-response"
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
        "id": "b1a2b3c4-d5e6-7890-abcd-ef1234567890",
        "name": "March 2026 Budget",
        "periodType": "MONTHLY",
        "startDate": "2026-03-01",
        "endDate": "2026-03-31",
        "totalAmount": 2000.00,
        "status": "ACTIVE",
        "createdAt": "2026-03-18T10:00:00Z"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "page": 0,
    "size": 20
  },
  "timestamp": "2026-03-18T10:10:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* PATCH /api/v1/budgets/{budgetId}/archive */}
			<div className="space-y-4">
				<h2 id="archive-budget" className="text-xl font-semibold text-[#F0EDF5]">
					{t("archive_budget")}
				</h2>
				<ApiEndpoint
					method="PATCH"
					path="/api/v1/budgets/{budgetId}/archive"
					description="Archives a budget, preventing further modifications"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="archive-budget-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "budgetId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the budget to archive",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="archive-budget-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X PATCH http://localhost:8080/api/v1/budgets/b1a2b3c4-d5e6-7890-abcd-ef1234567890/archive`,
									},
									{
										label: "JavaScript",
										code: `const budgetId = "b1a2b3c4-d5e6-7890-abcd-ef1234567890";
const response = await fetch(
  \`http://localhost:8080/api/v1/budgets/\${budgetId}/archive\`,
  { method: "PATCH" }
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.patch(
    "http://localhost:8080/api/v1/budgets/b1a2b3c4-d5e6-7890-abcd-ef1234567890/archive"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="archive-budget-response"
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
  "message": "Budget archived successfully",
  "data": null,
  "timestamp": "2026-03-18T10:15:00Z"
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
					label: "Notifications API",
					href: `/${locale}/develop/api/notifications`,
				}}
				next={{
					label: "Goals API",
					href: `/${locale}/develop/api/goals`,
				}}
			/>
		</div>
	);
}
