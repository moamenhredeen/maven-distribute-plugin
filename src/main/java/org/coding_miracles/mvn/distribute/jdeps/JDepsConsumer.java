package org.coding_miracles.mvn.distribute.jdeps;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * Consumes the output of the jdeps tool
 *
 * @author Robert Scholte
 *
 */
public class JDepsConsumer
		extends CommandLineUtils.StringStreamConsumer
		implements StreamConsumer
{

	/**
	 * JDK8 Windows: JDK internal API (rt.jar)
	 * JDK8 Linux:   JDK internal API (JDK removed internal API)
	 * JDK9:         JDK internal API (java.base)
	 */
	private static final Pattern JDKINTERNALAPI =
			Pattern.compile( ".+->\\s([a-z\\.]+)\\s+(JDK (?:removed )?internal API.*)" );

	/**
	 * <dl>
	 *  <dt>key</dt><dd>The offending package</dd>
	 *  <dt>value</dt><dd>Offending details</dd>
	 * </dl>
	 */
	private Map<String, String> offendingPackages = new HashMap<String, String>();

	private static final Pattern PROFILE = Pattern.compile( "\\s+->\\s([a-z\\.]+)\\s+(\\S+)" );

	/**
	 * <dl>
	 *  <dt>key</dt><dd>The package</dd>
	 *  <dt>value</dt><dd>The profile</dd>
	 * </dl>
	 */
	private Map<String, String> profiles = new HashMap<String, String>();


	public void consumeLine( String line )
	{
		super.consumeLine( line );
		Matcher matcher;

		matcher = JDKINTERNALAPI.matcher( line );
		if ( matcher.matches() )
		{
			offendingPackages.put( matcher.group( 1 ), matcher.group( 2 ) );
			return;
		}

		matcher = PROFILE.matcher( line );
		if ( matcher.matches() )
		{
			profiles.put( matcher.group( 1 ), matcher.group( 2 ) );
			return;
		}
	}

	public Map<String, String> getOffendingPackages()
	{
		return offendingPackages;
	}

	public Map<String, String> getProfiles()
	{
		return profiles;
	}

}
