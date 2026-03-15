"use client";

import React, { useEffect, useState } from 'react';
import dynamic from 'next/dynamic';
import 'swagger-ui-react/swagger-ui.css';

// SwaggerUI requires window object, so we load it dynamically with SSR disabled
const SwaggerUI = dynamic(() => import('swagger-ui-react'), { ssr: false });

export default function ApiReference() {
  const [basePath, setBasePath] = useState('');

  useEffect(() => {
    // In production (GitHub pages), the base path is /orbit-api
    const path = window.location.pathname.startsWith('/orbit-api') ? '/orbit-api' : '';
    setBasePath(path);
  }, []);

  return (
    <div className="card bg-base-100 shadow-xl border border-white/5">
      <div className="card-body p-0 sm:p-6 overflow-x-auto overflow-y-hidden">
        <SwaggerUI url={`${basePath}/openapi.json`} />
      </div>
    </div>
  );
}
