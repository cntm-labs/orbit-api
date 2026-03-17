"use client";

import { useLocale, useTranslations } from "next-intl";
import ApiEndpoint from "@/components/ApiEndpoint";
import Breadcrumb from "@/components/Breadcrumb";
import CodeBlock from "@/components/CodeBlock";
import PageNav from "@/components/PageNav";
import ParamTable from "@/components/ParamTable";

export default function CategoriesAPIPage() {
	const locale = useLocale();
	const t = useTranslations("pages");
	const ta = useTranslations("api");

	return (
		<div className="space-y-8">
			<Breadcrumb
				items={[
					{ label: "Develop", href: `/${locale}/develop/getting-started` },
					{ label: "API Reference" },
					{ label: t("categories_api_title").replace(" API", "").replace("API ", "") },
				]}
			/>

			<div>
				<h1 className="text-3xl font-bold text-[#F0EDF5] mb-2">{t("categories_api_title")}</h1>
				<p className="text-[#9B8FB8] leading-relaxed">{t("categories_api_desc")}</p>
			</div>

			{/* POST /api/v1/categories */}
			<div className="space-y-4">
				<h2 id="create-category" className="text-xl font-semibold text-[#F0EDF5]">
					{t("create_category")}
				</h2>
				<ApiEndpoint
					method="POST"
					path="/api/v1/categories"
					description="Creates a new category. If userId is omitted, it becomes a system category."
				>
					<div className="space-y-6">
						<div>
							<h3
								id="create-category-body"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("request_body")}
							</h3>
							<ParamTable
								params={[
									{
										name: "userId",
										type: "string (uuid)",
										required: false,
										description: "Owner UUID. If omitted, the category becomes a system category",
									},
									{
										name: "name",
										type: "string",
										required: true,
										description: 'Display name for the category (e.g., "Food & Dining")',
									},
									{
										name: "type",
										type: "string (enum)",
										required: true,
										description: "The category classification type",
										enumValues: ["INCOME", "EXPENSE", "TRANSFER"],
									},
									{
										name: "icon",
										type: "string",
										required: false,
										description: 'Icon identifier for the category (e.g., "utensils")',
									},
									{
										name: "color",
										type: "string",
										required: false,
										description: 'Hex color code for the category (e.g., "#FF5733")',
									},
									{
										name: "parentCategoryId",
										type: "string (uuid)",
										required: false,
										description: "Parent category UUID for creating subcategories",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-category-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X POST http://localhost:8080/api/v1/categories \\
  -H "Content-Type: application/json" \\
  -d '{
    "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "Food & Dining",
    "type": "EXPENSE",
    "icon": "utensils",
    "color": "#FF5733"
  }'`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch("http://localhost:8080/api/v1/categories", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({
    userId: "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    name: "Food & Dining",
    type: "EXPENSE",
    icon: "utensils",
    color: "#FF5733",
  }),
});

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.post(
    "http://localhost:8080/api/v1/categories",
    json={
        "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "name": "Food & Dining",
        "type": "EXPENSE",
        "icon": "utensils",
        "color": "#FF5733",
    },
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-category-response"
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
  "message": "Category created successfully",
  "data": {
    "id": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
    "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "Food & Dining",
    "type": "EXPENSE",
    "icon": "utensils",
    "color": "#FF5733",
    "isSystem": false,
    "parentCategoryId": null,
    "createdAt": "2026-03-15T10:00:00Z",
    "updatedAt": "2026-03-15T10:00:00Z"
  },
  "timestamp": "2026-03-15T10:00:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/categories/system */}
			<div className="space-y-4">
				<h2 id="get-system-categories" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_system_categories")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/categories/system"
					description="Retrieves all default system categories available to all users"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-system-categories-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/categories/system`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch(
  "http://localhost:8080/api/v1/categories/system"
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/categories/system"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-system-categories-response"
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
  "message": "System categories retrieved successfully",
  "data": [
    {
      "id": "d4e5f6a7-b8c9-0123-def0-456789abcdef",
      "userId": null,
      "name": "Food & Dining",
      "type": "EXPENSE",
      "icon": "utensils",
      "color": "#FF5733",
      "isSystem": true,
      "parentCategoryId": null,
      "createdAt": "2026-01-01T00:00:00Z",
      "updatedAt": "2026-01-01T00:00:00Z"
    },
    {
      "id": "e5f6a7b8-c9d0-1234-ef01-56789abcdef0",
      "userId": null,
      "name": "Salary",
      "type": "INCOME",
      "icon": "banknote",
      "color": "#4CAF50",
      "isSystem": true,
      "parentCategoryId": null,
      "createdAt": "2026-01-01T00:00:00Z",
      "updatedAt": "2026-01-01T00:00:00Z"
    }
  ],
  "timestamp": "2026-03-15T10:05:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/categories/user/{userId} */}
			<div className="space-y-4">
				<h2 id="get-user-categories" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_user_categories")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/categories/user/{userId}"
					description="Retrieves all custom categories created by a specific user"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-user-categories-params"
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
										description: "The UUID of the user whose categories to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-user-categories-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/categories/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch(
  "http://localhost:8080/api/v1/categories/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890"
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/categories/user/a1b2c3d4-e5f6-7890-abcd-ef1234567890"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-user-categories-response"
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
  "message": "User categories retrieved successfully",
  "data": [
    {
      "id": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
      "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "name": "Side Hustle",
      "type": "INCOME",
      "icon": "briefcase",
      "color": "#2196F3",
      "isSystem": false,
      "parentCategoryId": null,
      "createdAt": "2026-03-10T08:00:00Z",
      "updatedAt": "2026-03-10T08:00:00Z"
    }
  ],
  "timestamp": "2026-03-15T10:10:00Z"
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
					label: "Transactions API",
					href: `/${locale}/develop/api/transactions`,
				}}
				next={{
					label: "Notifications API",
					href: `/${locale}/develop/api/notifications`,
				}}
			/>
		</div>
	);
}
