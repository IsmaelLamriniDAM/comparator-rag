import { definePreset } from '@primeuix/themes';
import Aura from '@primeuix/themes/aura';

const MyPreset = definePreset(Aura, {
    semantic: {
        primary: {
            50: '#E6F7FF',
            100: '#B3E9FF',
            200: '#80DBFF',
            300: '#4DCDFF',
            400: '#39BDFF',
            500: '#64DDFF', 
            600: '#1FA9E0',
            700: '#1780B3',
            800: '#0F5786',
            900: '#073059'
        },
        colorScheme: {
            light: {
                surface: '#2a0f0fff',   
                background: '#0F172A', 
                border: '#334155',     
                focus: '#39BDFF',
                text: '#FFFFFF',
                textSecondary: '#94A3B8', 
                placeholder: '#64748B' 
            },
            dark: {
                surface: '#2a0f0fff',
                background: '#0F172A',
                border: '#334155',
                focus: '#39BDFF',
                text: '#FFFFFF',
                textSecondary: '#94A3B8',
                placeholder: '#64748B'
            }
        }
    },
    components: {
        select: {
            root: {
                background: 'rgba(15, 23, 42, 0.5)',
                borderColor: '#334155',
                color: '#FFFFFF',
                placeholderColor: '#64748B',
                focusBorderColor: '#39BDFF'
            },
            option: {
                color: '#FFFFFF',             
                focusBackground: '#1E293B',   
                focusColor: '#39BDFF',      
                selectedBackground: '#1E293B',
                selectedColor: '#64DDFF'    
            },
            overlay: {
                background: '#0F172A',
            }
        },
        slider: {
            root: {
                transitionDuration: '150ms'
            },
            track: {
                background: '#334155',
                borderRadius: '4px',
                size: '6px'
            },
            range: {
                background: '#39BDFF'
            },
            handle: {
                width: '16px',
                height: '16px',
                borderRadius: '50%',
                background: '#334155',
                hoverBackground: '#39BDFF',
                content: {
                    borderRadius: '50%',
                    background: '#FFFFFF',
                    hoverBackground: '#64DDFF',
                    width: '8px',
                    height: '8px',
                    shadow: '0 0 4px rgba(0,0,0,0.3)'
                },
                focusRing: {
                    width: '2px',
                    style: 'solid',
                    color: '#39BDFF',
                    offset: '2px',
                    shadow: '0 0 0 2px rgba(57,189,255,0.4)'
                }
            }
        },
    }
});

export default MyPreset;
