package ues.occ.proyeccion.social.ws.app.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@Component
public class PaginationDiscoverabilityListener  implements ApplicationListener<PaginatedResultsRetrievedEvent<?>> {

        @Override
        public void onApplicationEvent(final PaginatedResultsRetrievedEvent ev) {
            addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getPage(), ev.getTotalPages(), ev.getPageSize());
        }

        void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder, HttpServletResponse response,
                                                   final int page, final int totalPages, final int pageSize) {

            var completeUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            uriBuilder.path(completeUri.split("api")[1]);

            final StringBuilder linkHeader = new StringBuilder();
            if (hasNextPage(page, totalPages)) {
                final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
                linkHeader.append(createLinkHeader(uriForNextPage, "next"));
            }
            if (hasPreviousPage(page)) {
                final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
                appendCommaIfNecessary(linkHeader);
                linkHeader.append(createLinkHeader(uriForPrevPage, "prev"));
            }
            if (hasFirstPage(page)) {
                final String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
                appendCommaIfNecessary(linkHeader);
                linkHeader.append(createLinkHeader(uriForFirstPage, "first"));
            }
            if (hasLastPage(page, totalPages)) {
                final String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
                appendCommaIfNecessary(linkHeader);
                linkHeader.append(createLinkHeader(uriForLastPage, "last"));
            }
            response.addHeader("Link", linkHeader.toString());
        }

        String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
            return uriBuilder.replaceQueryParam("page", page + 1).replaceQueryParam("size", size).build().encode().toUriString();
        }

        String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
            return uriBuilder.replaceQueryParam("page", page - 1).replaceQueryParam("size", size).build().encode().toUriString();
        }

        String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
            return uriBuilder.replaceQueryParam("page", 0).replaceQueryParam("size", size).build().encode().toUriString();
        }

        String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
            return uriBuilder.replaceQueryParam("page", totalPages).replaceQueryParam("size", size).build().encode().toUriString();
        }

        boolean hasNextPage(final int page, final int totalPages) {
            return page < totalPages - 1;
        }

        boolean hasPreviousPage(final int page) {
            return page > 0;
        }

        boolean hasFirstPage(final int page) {
            return hasPreviousPage(page);
        }

        boolean hasLastPage(final int page, final int totalPages) {
            return totalPages > 1 && hasNextPage(page, totalPages);
        }

        void appendCommaIfNecessary(final StringBuilder linkHeader) {
            if (linkHeader.length() > 0) {
                linkHeader.append(", ");
            }
        }

        public static String createLinkHeader(final String uri, final String rel) {
            return "<" + uri + ">; rel=\"" + rel + "\"";
        }
}
