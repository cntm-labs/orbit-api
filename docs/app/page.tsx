import React from 'react';
import Link from 'next/link';

export default function Home() {
  return (
    <div className="max-w-4xl mx-auto space-y-8 animate-in fade-in duration-500">
      <div className="space-y-4">
        <h1 className="text-5xl font-extrabold tracking-tight">
          Welcome to <span className="bg-gradient-to-r from-success to-primary text-transparent bg-clip-text">Orbit</span>
        </h1>
        <p className="text-xl text-muted leading-relaxed">
          The elite, production-grade API for modern Personal Finance Management. 
          Built with Spring Boot 4, Java 25, and a strict Hexagonal Architecture.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-12">
        {/* Card 1 */}
        <Link href="/api-reference" className="card bg-base-100 shadow-xl border border-white/5 hover:border-primary/50 transition-colors cursor-pointer group">
          <div className="card-body">
            <div className="w-12 h-12 rounded-xl bg-primary/10 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6 text-primary" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" /></svg>
            </div>
            <h2 className="card-title text-foreground">API Reference</h2>
            <p className="text-muted text-sm mt-2">Interactive Swagger documentation for all REST endpoints, DTOs, and schemas.</p>
          </div>
        </Link>

        {/* Card 2 */}
        <Link href="/architecture" className="card bg-base-100 shadow-xl border border-white/5 hover:border-success/50 transition-colors cursor-pointer group">
          <div className="card-body">
            <div className="w-12 h-12 rounded-xl bg-success/10 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6 text-success" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 002-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" /></svg>
            </div>
            <h2 className="card-title text-foreground">Architecture</h2>
            <p className="text-muted text-sm mt-2">Explore the Hexagonal design, folder structures, and database schemas.</p>
          </div>
        </Link>
      </div>

      <div className="mt-12 p-6 rounded-2xl bg-gradient-to-br from-base-100 to-base-300 border border-white/5 shadow-inner">
        <h3 className="text-lg font-bold text-foreground mb-4">Quick Start</h3>
        <div className="bg-[#0D0B1A] p-4 rounded-lg font-mono text-sm text-success overflow-x-auto">
          <code>
            git clone https://github.com/MrBT-nano/orbit-api.git<br/>
            cd orbit-api<br/>
            ./mvnw spring-boot:run
          </code>
        </div>
      </div>
    </div>
  );
}
