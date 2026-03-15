"use client";

import React, { useState } from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

export default function Sidebar() {
  const pathname = usePathname();
  const [isOpen, setIsOpen] = useState(false);

  const navItems = [
    { name: 'Introduction', path: '/' },
    { name: 'Architecture', path: '/architecture' },
    { name: 'API Reference', path: '/api-reference' },
  ];

  return (
    <>
      {/* Mobile toggle */}
      <div className="lg:hidden p-4 bg-base-100 border-b border-white/5 flex justify-between items-center sticky top-0 z-50">
        <div className="font-bold text-lg text-primary">Orbit Docs</div>
        <button onClick={() => setIsOpen(!isOpen)} className="btn btn-square btn-ghost">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-5 h-5 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16"></path></svg>
        </button>
      </div>

      {/* Sidebar */}
      <div className={`fixed inset-y-0 left-0 z-40 w-64 bg-base-100 border-r border-white/5 transform transition-transform duration-300 ease-in-out lg:translate-x-0 lg:static lg:inset-auto ${isOpen ? 'translate-x-0' : '-translate-x-full'}`}>
        <div className="h-full flex flex-col">
          <div className="p-6 hidden lg:block">
            <div className="font-bold text-2xl bg-gradient-to-r from-success to-primary text-transparent bg-clip-text">Orbit API</div>
            <div className="text-xs text-muted mt-1">Personal Finance Engine</div>
          </div>
          
          <nav className="flex-1 px-4 py-4 space-y-2 overflow-y-auto">
            <div className="text-xs font-semibold text-muted uppercase tracking-wider mb-4 px-2">Documentation</div>
            {navItems.map((item) => {
              const isActive = pathname === item.path || (pathname === '/orbit-api' && item.path === '/');
              return (
                <Link key={item.name} href={item.path} onClick={() => setIsOpen(false)}>
                  <div className={`px-4 py-2 rounded-lg transition-colors duration-200 ${isActive ? 'bg-primary/10 text-primary font-medium border border-primary/20' : 'text-muted hover:bg-base-200 hover:text-foreground'}`}>
                    {item.name}
                  </div>
                </Link>
              );
            })}
          </nav>
          
          <div className="p-4 border-t border-white/5">
            <a href="https://github.com/MrBT-nano/orbit-api" target="_blank" rel="noopener noreferrer" className="btn btn-outline btn-primary w-full text-sm">
              GitHub Repository
            </a>
          </div>
        </div>
      </div>
      
      {/* Overlay for mobile */}
      {isOpen && (
        <div className="fixed inset-0 bg-black/50 z-30 lg:hidden" onClick={() => setIsOpen(false)} />
      )}
    </>
  );
}
