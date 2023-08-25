package sn.esmt.tasks.taskmanager.exceptions;

public class StorageFileNotFoundException extends StorageException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -550895029635715528L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class StorageException extends RuntimeException {

        /**
         *
         */
        private static final long serialVersionUID = 310267515985726037L;

        public StorageException(String message) {
            super(message);
        }

        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
