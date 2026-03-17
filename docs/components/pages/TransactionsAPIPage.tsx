"use client";

import { useLocale, useTranslations } from "next-intl";
import ApiEndpoint from "@/components/ApiEndpoint";
import Breadcrumb from "@/components/Breadcrumb";
import CodeBlock from "@/components/CodeBlock";
import PageNav from "@/components/PageNav";
import ParamTable from "@/components/ParamTable";

export default function TransactionsAPIPage() {
	const locale = useLocale();
	const t = useTranslations("pages");
	const ta = useTranslations("api");

	return (
		<div className="space-y-8">
			<Breadcrumb
				items={[
					{ label: "Develop", href: `/${locale}/develop/getting-started` },
					{ label: "API Reference" },
					{ label: t("transactions_api_title").replace(" API", "").replace("API ", "") },
				]}
			/>

			<div>
				<h1 className="text-3xl font-bold text-[#F0EDF5] mb-2">{t("transactions_api_title")}</h1>
				<p className="text-[#9B8FB8] leading-relaxed">{t("transactions_api_desc")}</p>
			</div>

			{/* POST /api/v1/transactions */}
			<div className="space-y-4">
				<h2 id="create-transaction" className="text-xl font-semibold text-[#F0EDF5]">
					{t("create_transaction")}
				</h2>
				<ApiEndpoint
					method="POST"
					path="/api/v1/transactions"
					description="Records a new financial transaction. Automatically updates the associated account's balance."
				>
					<div className="space-y-6">
						<div>
							<h3
								id="create-transaction-body"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("request_body")}
							</h3>
							<ParamTable
								params={[
									{
										name: "accountId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the account this transaction belongs to",
									},
									{
										name: "categoryId",
										type: "string (uuid)",
										required: false,
										description: "Optional category UUID to classify this transaction",
									},
									{
										name: "amount",
										type: "number",
										required: true,
										description: "Transaction amount. Positive for income, negative for expenses",
									},
									{
										name: "currencyCode",
										type: "string",
										required: true,
										description: "ISO 4217 currency code or crypto symbol (e.g., USD, BTC)",
									},
									{
										name: "description",
										type: "string",
										required: false,
										description: "Human-readable description of the transaction",
									},
									{
										name: "transactionDate",
										type: "string (ISO 8601)",
										required: false,
										description: "Date and time of the transaction. Defaults to now if omitted",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-transaction-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl -X POST http://localhost:8080/api/v1/transactions \\
  -H "Content-Type: application/json" \\
  -d '{
    "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
    "categoryId": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
    "amount": -45.50,
    "currencyCode": "USD",
    "description": "Grocery shopping",
    "transactionDate": "2026-03-15T14:30:00Z"
  }'`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch("http://localhost:8080/api/v1/transactions", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({
    accountId: "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
    categoryId: "c1d2e3f4-a5b6-7890-cdef-123456789abc",
    amount: -45.50,
    currencyCode: "USD",
    description: "Grocery shopping",
    transactionDate: "2026-03-15T14:30:00Z",
  }),
});

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.post(
    "http://localhost:8080/api/v1/transactions",
    json={
        "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
        "categoryId": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
        "amount": -45.50,
        "currencyCode": "USD",
        "description": "Grocery shopping",
        "transactionDate": "2026-03-15T14:30:00Z",
    },
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="create-transaction-response"
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
  "message": "Transaction created successfully",
  "data": {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
    "categoryId": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
    "amount": -45.50,
    "currencyCode": "USD",
    "exchangeRate": null,
    "description": "Grocery shopping",
    "transactionDate": "2026-03-15T14:30:00Z",
    "status": "COMPLETED",
    "isReviewed": false,
    "createdAt": "2026-03-15T14:30:00Z"
  },
  "timestamp": "2026-03-15T14:30:00Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/transactions/{transactionId} */}
			<div className="space-y-4">
				<h2 id="get-transaction-by-id" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_transaction_by_id")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/transactions/{transactionId}"
					description="Retrieves a specific transaction record"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-transaction-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "transactionId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the transaction to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-transaction-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/transactions/a1b2c3d4-e5f6-7890-abcd-ef1234567890`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch(
  "http://localhost:8080/api/v1/transactions/a1b2c3d4-e5f6-7890-abcd-ef1234567890"
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/transactions/a1b2c3d4-e5f6-7890-abcd-ef1234567890"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-transaction-response"
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
  "message": "Transaction retrieved successfully",
  "data": {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
    "categoryId": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
    "amount": -45.50,
    "currencyCode": "USD",
    "exchangeRate": null,
    "description": "Grocery shopping",
    "transactionDate": "2026-03-15T14:30:00Z",
    "status": "COMPLETED",
    "isReviewed": false,
    "createdAt": "2026-03-15T14:30:00Z"
  },
  "timestamp": "2026-03-15T14:30:01Z"
}`,
									},
								]}
							/>
						</div>
					</div>
				</ApiEndpoint>
			</div>

			{/* GET /api/v1/transactions/account/{accountId} */}
			<div className="space-y-4">
				<h2 id="get-transactions-by-account" className="text-xl font-semibold text-[#F0EDF5]">
					{t("get_transactions_by_account")}
				</h2>
				<ApiEndpoint
					method="GET"
					path="/api/v1/transactions/account/{accountId}"
					description="Retrieves the ledger history for a specific account"
				>
					<div className="space-y-6">
						<div>
							<h3
								id="get-account-transactions-params"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("path_params")}
							</h3>
							<ParamTable
								params={[
									{
										name: "accountId",
										type: "string (uuid)",
										required: true,
										description: "The UUID of the account whose transactions to retrieve",
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-account-transactions-example"
								className="text-sm font-semibold text-[#9B8FB8] uppercase tracking-wider mb-3"
							>
								{ta("example_request")}
							</h3>
							<CodeBlock
								tabs={[
									{
										label: "cURL",
										code: `curl http://localhost:8080/api/v1/transactions/account/f7e6d5c4-b3a2-1908-fedc-ba0987654321`,
									},
									{
										label: "JavaScript",
										code: `const response = await fetch(
  "http://localhost:8080/api/v1/transactions/account/f7e6d5c4-b3a2-1908-fedc-ba0987654321"
);

const data = await response.json();
console.log(data);`,
									},
									{
										label: "Python",
										code: `import requests

response = requests.get(
    "http://localhost:8080/api/v1/transactions/account/f7e6d5c4-b3a2-1908-fedc-ba0987654321"
)

print(response.json())`,
									},
								]}
							/>
						</div>

						<div>
							<h3
								id="get-account-transactions-response"
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
  "message": "Transactions retrieved successfully",
  "data": [
    {
      "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
      "categoryId": "c1d2e3f4-a5b6-7890-cdef-123456789abc",
      "amount": -45.50,
      "currencyCode": "USD",
      "exchangeRate": null,
      "description": "Grocery shopping",
      "transactionDate": "2026-03-15T14:30:00Z",
      "status": "COMPLETED",
      "isReviewed": false,
      "createdAt": "2026-03-15T14:30:00Z"
    },
    {
      "id": "b2c3d4e5-f6a7-8901-bcde-f12345678901",
      "accountId": "f7e6d5c4-b3a2-1908-fedc-ba0987654321",
      "categoryId": null,
      "amount": 3500.00,
      "currencyCode": "USD",
      "exchangeRate": null,
      "description": "Monthly salary",
      "transactionDate": "2026-03-01T09:00:00Z",
      "status": "COMPLETED",
      "isReviewed": true,
      "createdAt": "2026-03-01T09:00:00Z"
    }
  ],
  "timestamp": "2026-03-15T14:35:00Z"
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
					label: "Accounts API",
					href: `/${locale}/develop/api/accounts`,
				}}
				next={{
					label: "Categories API",
					href: `/${locale}/develop/api/categories`,
				}}
			/>
		</div>
	);
}
