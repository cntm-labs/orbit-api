"use client";

import React, { useEffect, useState } from 'react';
import dynamic from 'next/dynamic';
import 'swagger-ui-react/swagger-ui.css';

// SwaggerUI requires window object, so we load it dynamically with SSR disabled
const SwaggerUI = dynamic(() => import('swagger-ui-react'), { ssr: false });

export default function Home() {
  const [basePath, setBasePath] = useState('');

  useEffect(() => {
    // In production (GitHub pages), the base path is /orbit-api
    const path = window.location.pathname.startsWith('/orbit-api') ? '/orbit-api' : '';
    setBasePath(path);
  }, []);

  return (
    <div className="min-h-screen bg-base-200 flex flex-col">
      <div className="navbar bg-base-100 shadow-sm border-b">
        <div className="flex-1">
          <a className="btn btn-ghost text-xl text-primary">Orbit API Reference</a>
        </div>
        <div className="flex-none gap-2">
          <div className="dropdown dropdown-end">
            <label tabIndex={0} className="btn btn-ghost btn-circle avatar">
              <div className="w-10 rounded-full">
                <img alt="User" src="https://ui-avatars.com/api/?name=Orbit&background=0D8ABC&color=fff" />
              </div>
            </label>
          </div>
        </div>
      </div>
      
      <main className="flex-grow p-4 md:p-8 w-full max-w-7xl mx-auto">
        <div className="card bg-base-100 shadow-xl">
          <div className="card-body p-0 sm:p-6 overflow-x-auto overflow-y-hidden">
            {/* The Swagger UI component will fetch from our statically generated JSON */}
            <SwaggerUI url={`${basePath}/openapi.json`} />
          </div>
        </div>
      </main>

      <footer className="footer footer-center p-4 bg-base-300 text-base-content mt-8">
        <div>
          <p>Orbit Personal Finance Management System</p>
        </div>
      </footer>
    </div>
  );
}
