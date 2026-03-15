import React from 'react';

export default function ArchitecturePage() {
  return (
    <div className="max-w-4xl mx-auto space-y-8 animate-in fade-in duration-500">
      <div className="space-y-4">
        <h1 className="text-4xl font-bold text-foreground">Architecture</h1>
        <p className="text-lg text-muted">
          Orbit relies on a strict, clean-code foundation leveraging Java 25, Spring Boot 4, and Hexagonal (Ports & Adapters) boundaries.
        </p>
      </div>

      <div className="prose prose-invert max-w-none">
        <div className="card bg-base-100 shadow-xl border border-white/5 mb-8">
          <div className="card-body">
            <h2 className="text-xl font-bold text-primary mb-4 border-b border-white/5 pb-2">Core Principles</h2>
            <ul className="space-y-3 text-muted">
              <li><strong className="text-foreground">Package by Feature:</strong> Code is grouped by domain (e.g., `ledger`, `security`) rather than technical layer.</li>
              <li><strong className="text-foreground">Hexagonal Boundaries:</strong> Strict separation of `api` (Inbound), `core` (Business Logic), and `infrastructure` (Outbound/Database).</li>
              <li><strong className="text-foreground">CQRS Lite:</strong> Business logic is divided into highly specific Use Cases (e.g., `CreateAccountUseCase`) instead of monolithic God Services.</li>
              <li><strong className="text-foreground">Java 25 Records:</strong> All DTOs and immutable data carriers utilize modern Java `record` types.</li>
            </ul>
          </div>
        </div>

        <div className="card bg-base-100 shadow-xl border border-white/5">
          <div className="card-body">
            <h2 className="text-xl font-bold text-success mb-4 border-b border-white/5 pb-2">Database Strategy</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <h3 className="text-lg font-semibold text-foreground flex items-center gap-2">
                  <span className="w-3 h-3 rounded-full bg-primary"></span>
                  PostgreSQL
                </h3>
                <p className="text-sm mt-2 text-muted">Handles all core transactional and financial data. Ensures ACID compliance for the Ledger, Payments, and User Identity.</p>
              </div>
              <div>
                <h3 className="text-lg font-semibold text-foreground flex items-center gap-2">
                  <span className="w-3 h-3 rounded-full bg-success"></span>
                  MongoDB
                </h3>
                <p className="text-sm mt-2 text-muted">Handles high-volume, schema-less data such as User Analytics, AI Chat History, and Recommendation Profiles.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
