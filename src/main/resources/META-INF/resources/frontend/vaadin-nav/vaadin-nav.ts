import {
  LitElement,
  html,
  TemplateResult,
  property,
  customElement,
} from "lit-element";
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-rich-text-editor/vaadin-rich-text-editor.js'
import styles from "./vaadin-nav.css";
// @ts-ignore
import * as glyphs from "./vaadin-icons-bundle.js";

interface NavItem {
  path: string;
  title: string;
  icon?: string;
  badge?: number;
  children?: NavItem[];
}

@customElement("vaadin-nav")
class VaadinNav extends LitElement {
  static styles = [styles];
  
  @property({ type: Array })
  items: NavItem[] = [];
  // Exxample of using items in TS.
  // items = [
  //   {
  //     path: "#general",
  //     component: "home-view",
  //     title: "general",
  //     icon: "hash",
  //   },
  //   {
  //     path: "#announcements",
  //     component: "people-view",
  //     title: "announcements",
  //     icon: "hash",
  //     badge: 2,
  //     // action: async () => {
  //     //   await import('./views/people/people-view');
  //     // },
  //   },
  // ];

  @property()
  label: String | undefined = undefined;

  render(): TemplateResult {
    return html`
      ${this.label ? html`<div class="label">${this.label}</div>` : html``}
      <nav aria-label="${this.label ?? ""}">
        <ul>
          ${this.items.map((item) => this.childTemplate(item))}
        </ul>
      </nav>
    `;
  }
  // ${this.renderItems(this.items)}

  childTemplate(item: NavItem): TemplateResult {
    return html` <li class="${item.path === "home" ? "active" : ""}">
      <div class="title-and-toggle">
        <a href="${item.path}">
          <!-- should be a proper "icon" component eventually, mapped from an 'icon' property in the routes -->
          ${item.icon ? html`<svg>${glyphs[item.icon]}</svg>` : html``}

          <div class="title">${item.title}</div>
          ${item.badge ? html`<div class="badge">${item.badge}</div>` : html``}
        </a>

        ${item.children
          ? html` <button
              aria-label="expand/collapse sub-menu for ${item.title}"
              class="toggle"
              @click="${this.togglePressed}"
            >
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M5.50087 7.33287C5.13081 7.00102 4.56181 7.032 4.22996 7.40205C3.89811 7.77211 3.92909 8.34111 4.29914 8.67296L9.24176 13.2085C9.58366 13.5151 10.1016 13.5151 10.4435 13.2085L15.5009 8.57005C15.8709 8.23821 15.9019 7.6692 15.57 7.29914C15.2382 6.92909 14.6692 6.89811 14.2991 7.22996L9.84262 11.3296L5.50087 7.33287V7.33287Z"
                  fill="currentColor"
                  fill-opacity="0.6"
                />
              </svg>
            </button>`
          : html``}
      </div>

      ${item.children
        ? html`<ul>
            ${item.children.map((subitem) => this.childTemplate(subitem))}
          </ul>`
        : html``}
    </li>`;
  }
  constructor() {
    super();
  }

  connectedCallback() {
    super.connectedCallback();
    this.addEventListener("click", (e: MouseEvent) => {
      if (
        e.composedPath().find((el) => (<Element>el).nodeName === "A")
      ) {
        this.toggleActive(
          <Element>(
            e.composedPath().find((el) => (<Element>el).nodeName === "LI")
          )
        );
      }
    });
  }

  togglePressed(e : CustomEvent){
    const target = <Element>e.composedPath()[0];
    (<Element>target.parentNode!.parentNode!).classList.toggle("open");
  }

  toggleActive(active: Element) {
    Array.from(this.querySelectorAll("li")).forEach((li) => {
      li.classList.remove("active");
      if (li === active) {
        active.classList.add("active");
      }
    });
  }
}
export default VaadinNav;
